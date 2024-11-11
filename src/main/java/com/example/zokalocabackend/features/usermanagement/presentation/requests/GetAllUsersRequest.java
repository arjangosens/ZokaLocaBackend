package com.example.zokalocabackend.features.usermanagement.presentation.requests;

import com.example.zokalocabackend.features.usermanagement.domain.UserRole;

public record GetAllUsersRequest(
        Integer page,
        Integer pageSize,
        String sortBy,
        String sortOrder,
        String firstName,
        String lastName,
        String fullName,
        String email,
        UserRole role,
        String branchId
) {
    public GetAllUsersRequest {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "firstName";
        }
        if (sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "ASC";
        }
    }
}
