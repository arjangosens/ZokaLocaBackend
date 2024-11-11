package com.example.zokalocabackend.features.usermanagement.presentation.requests;

public record GetAllBranchesRequest(
        Integer page,
        Integer pageSize,
        String sortBy,
        String sortOrder,
        String name
) {
    public GetAllBranchesRequest {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "name";
        }
        if (sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "ASC";
        }
    }
}
