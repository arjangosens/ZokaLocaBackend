package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateBranchRequest(
        @NotBlank
        String name,

        List<String> userIds
) {
}
