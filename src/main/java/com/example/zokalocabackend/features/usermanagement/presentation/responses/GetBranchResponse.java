package com.example.zokalocabackend.features.usermanagement.presentation.responses;

import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.UserCollectionItemDTO;

import java.util.List;

public record GetBranchResponse(
        String id,
        String name,
        List<UserCollectionItemDTO> users
) {
}
