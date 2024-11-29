package com.example.zokalocabackend.features.assetmanagement;

import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import com.example.zokalocabackend.features.assetmanagement.persistence.AssetRepository;
import com.example.zokalocabackend.features.assetmanagement.persistence.MinioRepository;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssetService {
    private final MinioRepository minioRepository;
    private final AssetRepository assetRepository;

    public Asset createAsset(Asset asset, InputStream inputStream) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        String bucketName = asset.getLocation().toString();
        minioRepository.ensureBucketExists(bucketName);
        minioRepository.uploadFile(bucketName, asset.getName(), inputStream, asset.getContentType());
        return assetRepository.save(asset);
    }

    public InputStream downloadAssetData(Asset asset) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        String bucketName = asset.getLocation().toString();
        return minioRepository.downloadFile(bucketName, asset.getName());
    }

    public Asset getAssetById(String assetId) {
        return assetRepository.findById(assetId).orElseThrow();
    }
}