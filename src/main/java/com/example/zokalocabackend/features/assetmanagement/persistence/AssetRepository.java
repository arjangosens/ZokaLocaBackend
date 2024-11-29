package com.example.zokalocabackend.features.assetmanagement.persistence;

import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetRepository extends MongoRepository<Asset, String> {
}
