package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateBranchRequest(@NotBlank String name) {
}
