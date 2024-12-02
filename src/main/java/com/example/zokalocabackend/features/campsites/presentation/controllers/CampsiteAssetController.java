package com.example.zokalocabackend.features.campsites.presentation.controllers;

import com.example.zokalocabackend.features.assetmanagement.AssetService;
import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.presentation.mappers.AssetMapper;
import com.example.zokalocabackend.features.campsites.presentation.requests.AddCampsiteAssetRequest;
import com.example.zokalocabackend.features.campsites.presentation.requests.UpdateCampsiteAssetRequest;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampsiteAssetResponse;
import com.example.zokalocabackend.features.campsites.services.CampsiteService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campsites/{campsiteId}/assets")
public class CampsiteAssetController {
    private final CampsiteService campsiteService;
    private final AssetService assetService;

    @GetMapping()
    public ResponseEntity<List<GetCampsiteAssetResponse>> getCampsiteAssets(@PathVariable String campsiteId) {
        Campsite campsite = campsiteService.getCampsiteById(campsiteId);
        List<GetCampsiteAssetResponse> responsesList = new ArrayList<>();

        for (String assetId : campsite.getImageIds()) {
            boolean isThumbnail = assetId.equals(campsite.getThumbnailAssetId());
            responsesList.add(new GetCampsiteAssetResponse(assetId, assetService.getAssetById(assetId).getName(), isThumbnail));
        }

        return ResponseEntity.ok(responsesList);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> addCampsiteAsset(@PathVariable String campsiteId, @ModelAttribute AddCampsiteAssetRequest request) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        Campsite campsite = campsiteService.getCampsiteById(campsiteId);
        Asset asset = AssetMapper.toAsset(request, campsite.getId());
        asset = assetService.createAsset(asset, request.file().getInputStream());
        Set<String> campsiteImageIds = campsite.getImageIds();
        campsiteImageIds.add(asset.getId());
        campsite.setImageIds(campsiteImageIds);

        if (request.isThumbnail()) {
            campsite.setThumbnailAssetId(asset.getId());
        }

        campsiteService.updateCampsite(campsite.getId(), campsite);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{assetId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateCampsiteAsset(@PathVariable String campsiteId, @PathVariable String assetId, @ModelAttribute UpdateCampsiteAssetRequest request) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        Campsite campsite = campsiteService.getCampsiteById(campsiteId);
        Asset asset = assetService.getAssetById(assetId);

        if (!campsite.getImageIds().contains(assetId)) {
            throw new IllegalArgumentException("Asset does not belong to campsite");
        }

        boolean isAssetInfoChanged = false;

        if (request.file() != null) {
            assetService.updateAssetContent(assetId, request.file().getInputStream());
            asset.setContentType(request.file().getContentType());
            isAssetInfoChanged = true;
        }

        if (StringUtils.hasText(request.name())) {
            asset.setName(request.name());
            isAssetInfoChanged = true;
        }

        if (request.isThumbnail()) {
            campsite.setThumbnailAssetId(asset.getId());
            campsiteService.updateCampsite(campsite.getId(), campsite);
        }

        if (isAssetInfoChanged) {
            assetService.updateAssetInfo(assetId, asset);
        }

        campsiteService.updateCampsite(campsite.getId(), campsite);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<?> deleteCampsiteAsset(@PathVariable String campsiteId, @PathVariable String assetId) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        Campsite campsite = campsiteService.getCampsiteById(campsiteId);
        Asset asset = assetService.getAssetById(assetId);

        if (!campsite.getImageIds().contains(assetId)) {
            throw new IllegalArgumentException("Asset does not belong to campsite");
        }

        if (assetId.equals(campsite.getThumbnailAssetId())) {
            campsite.setThumbnailAssetId(null);
            campsiteService.updateCampsite(campsite.getId(), campsite);
        }

        Set<String> campsiteImageIds = campsite.getImageIds();
        campsiteImageIds.remove(assetId);
        campsite.setImageIds(campsiteImageIds);
        campsiteService.updateCampsite(campsite.getId(), campsite);

        assetService.deleteAsset(assetId);
        return ResponseEntity.ok().build();
    }
}
