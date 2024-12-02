package com.example.zokalocabackend.features.assetmanagement.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AssetLocation {
    private String id;

    @NotNull
    private AssetLocationType locationType;

    public AssetLocation(AssetLocationType locationType) {
        this.locationType = locationType;
    }

    public AssetLocation(String id, AssetLocationType locationType) {
        this.id = id;
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return id.isEmpty() ? locationType.toString().toLowerCase() : locationType.toString().toLowerCase() + "-" + id;
    }

    public static AssetLocation fromString(String location) {
        String[] parts = location.split("-");
        AssetLocation assetLocation;
        if (parts.length == 1) {
            assetLocation = new AssetLocation(AssetLocationType.valueOf(parts[0].toUpperCase()));
        } else {
            assetLocation = new AssetLocation(parts[1], AssetLocationType.valueOf(parts[0].toUpperCase()));
        }

        return assetLocation;
    }
}
