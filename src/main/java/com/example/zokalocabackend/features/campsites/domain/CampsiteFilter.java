package com.example.zokalocabackend.features.campsites.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampsiteFilter {
    private String name;
    private String campsiteType;
    private Integer minDistanceInKm;
    private Integer maxDistanceInKm;
    private Integer numOfPeople;
    private List<String> facilityIds;
    private Integer minNumOfToilets;
    private Integer minNumOfShowers;
    private Integer minNumOfWaterSources;
    private Integer minNumOfRooms;
    private Integer minNumOfCommonAreas;
    private Integer minSizeSquareMeters;
}
