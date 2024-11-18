package com.example.zokalocabackend.features.visits.presentation.responses;

import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;

public record GetVisitWithBranchResponse(
        String id,
        String arrivalDate,
        String departureDate,
        int rating,
        String branchId,
        String campsiteId,
        Integer numOfPeople,
        Double price,
        String description,
        String[] pros,
        String[] cons,
        BranchCollectionItemDTO branch
) {
}
