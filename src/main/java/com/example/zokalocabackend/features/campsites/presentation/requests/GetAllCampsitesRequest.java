package com.example.zokalocabackend.features.campsites.presentation.requests;

import java.util.List;

public record GetAllCampsitesRequest(
        Integer page,
        Integer pageSize,
        String sortBy,
        String sortOrder,
        String name,
        String campsiteType,
        Integer minDistanceInKm,
        Integer maxDistanceInKm,
        Integer numOfPeople,
        List<String> facilityIds,
        Integer minNumOfToilets,
        Integer minNumOfShowers,
        Integer minNumOfWaterSources,
        Integer minNumOfRooms,
        Integer minNumOfCommonAreas,
        Integer minSizeSquareMeters
        ) {
    public GetAllCampsitesRequest {
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
