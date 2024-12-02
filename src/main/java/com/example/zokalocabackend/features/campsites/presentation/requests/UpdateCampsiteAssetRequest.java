package com.example.zokalocabackend.features.campsites.presentation.requests;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UpdateCampsiteAssetRequest(
        MultipartFile file,
        Boolean isThumbnail,
        String name
) {
}
