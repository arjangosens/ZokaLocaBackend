package com.example.zokalocabackend.features.campsites.presentation.responses;

public record GetCampsiteAssetResponse(
        String id,
        String name,
        boolean isThumbnail
) {
}
