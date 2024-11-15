package com.example.zokalocabackend.features.usermanagement.presentation.responses;

import com.example.zokalocabackend.features.usermanagement.domain.UserRole;

public record GetAllUsersListItemResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {
}
