package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record RegisterUserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        UserRole role,

        List<String> branchIds
) {
}
