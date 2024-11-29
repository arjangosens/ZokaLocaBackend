package com.example.zokalocabackend.features.assetmanagement.persistence;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Repository
public class MinioRepository {

    @Autowired
    private MinioClient minioClient;

    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build()
        );
    }

    public InputStream downloadFile(String bucketName, String objectName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    public void ensureBucketExists(String bucketName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }
}