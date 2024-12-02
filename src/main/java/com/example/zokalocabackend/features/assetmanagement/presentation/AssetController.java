package com.example.zokalocabackend.features.assetmanagement.presentation;

import com.example.zokalocabackend.features.assetmanagement.AssetService;
import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {
    private final AssetService assetService;

    @GetMapping("/{assetId}")
    public ResponseEntity<?> getAsset(@PathVariable String assetId) {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/{assetId}/download")
    public ResponseEntity<?> downloadAsset(@PathVariable String assetId) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        Asset asset = assetService.getAssetById(assetId);
        InputStreamResource resource = new InputStreamResource(assetService.downloadAssetData(asset));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + asset.getName())
                .contentType(MediaType.parseMediaType(asset.getContentType()))
                .body(resource);
    }
}