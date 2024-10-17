package com.example.zokalocabackend.campsites.presentation.requests;

import java.util.List;

public record GetAllCampsitesRequest(
        Integer page,
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
}
