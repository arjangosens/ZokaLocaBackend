package com.example.zokalocabackend.features.usermanagement.presentation.responses;

import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;

import java.util.List;

public record GetUserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        List<BranchCollectionItemDTO> branches
) {
}
