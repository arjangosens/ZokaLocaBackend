package com.example.zokalocabackend.features.campsites.presentation.controllers;

import com.example.zokalocabackend.features.assetmanagement.AssetService;
import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.presentation.mappers.AssetMapper;
import com.example.zokalocabackend.features.campsites.presentation.requests.AddCampsiteAssetRequest;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampsiteAssetResponse;
import com.example.zokalocabackend.features.campsites.services.CampsiteService;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
}
