package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserRequest(
        @NotNull
        UserRole role,
        
        List<String> branchIds) {
}
