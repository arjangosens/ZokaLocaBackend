package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) { }
