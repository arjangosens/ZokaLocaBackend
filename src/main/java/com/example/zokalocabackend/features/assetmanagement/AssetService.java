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
        Asset savedAsset = assetRepository.save(asset);
        String bucketName = savedAsset.getLocation().toString();
        minioRepository.ensureBucketExists(bucketName);
        minioRepository.uploadFile(bucketName, savedAsset.getId(), inputStream, savedAsset.getContentType());
        return savedAsset;
    }

    public InputStream downloadAssetData(Asset asset) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        String bucketName = asset.getLocation().toString();
        return minioRepository.downloadFile(bucketName, asset.getId());
    }

    public Asset getAssetById(String assetId) {
        return assetRepository.findById(assetId).orElseThrow();
    }

    public void updateAssetInfo(String assetId, Asset asset) {
        assetRepository.save(asset);
    }

    public void updateAssetContent(String assetId, InputStream inputStream) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        Asset asset = getAssetById(assetId);
        String bucketName = asset.getLocation().toString();
        minioRepository.uploadFile(bucketName, assetId, inputStream, asset.getContentType());
    }

    public void deleteAsset(String assetId) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        Asset asset = getAssetById(assetId);
        String bucketName = asset.getLocation().toString();
        minioRepository.deleteFile(bucketName, assetId);
        assetRepository.deleteById(assetId);
    }
}