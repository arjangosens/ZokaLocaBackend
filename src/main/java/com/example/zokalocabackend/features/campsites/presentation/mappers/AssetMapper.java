package com.example.zokalocabackend.features.campsites.presentation.mappers;

import com.example.zokalocabackend.features.assetmanagement.domain.Asset;
import com.example.zokalocabackend.features.assetmanagement.domain.AssetLocation;
import com.example.zokalocabackend.features.assetmanagement.domain.AssetLocationType;
import com.example.zokalocabackend.features.campsites.presentation.requests.AddCampsiteAssetRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssetMapper {

    public static Asset toAsset(AddCampsiteAssetRequest request, String campsiteId) {
        AssetLocation location = new AssetLocation(campsiteId, AssetLocationType.CAMPSITE);
        return new Asset(null, request.name(), request.file().getContentType(), location);
    }
}
