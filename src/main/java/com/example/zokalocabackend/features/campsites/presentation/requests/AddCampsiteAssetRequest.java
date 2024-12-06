package com.example.zokalocabackend.features.campsites.presentation.requests;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AddCampsiteAssetRequest(
        @NotNull
        MultipartFile file,

        boolean isThumbnail,

        String name
) {

    public AddCampsiteAssetRequest {
        if (name == null || name.isEmpty()) {
            name = file.getOriginalFilename();
        }
    }
}
