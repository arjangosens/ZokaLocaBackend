package com.example.zokalocabackend.campsites.presentation.datatransferobjects;

import com.example.zokalocabackend.campsites.domain.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@SuperBuilder
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "campsiteType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BuildingDTO.class, name = "BUILDING"),
        @JsonSubTypes.Type(value = FieldDTO.class, name = "FIELD")
})
public abstract class CampsiteDTO {
    private final String id;
    private final String name;
    private final String description;
    private final AddressDTO address;
    private final PersonLimitDTO personLimit;
    private final CampsitePriceDTO price;
    private final String arrivalTime;
    private final String departureTime;
    private final int numOfToilets;
    private final int numOfShowers;
    private final int numOfWaterSources;
    private final HashMap<String, SurroundingProximity> surroundings;
    private final List<String> facilityIds;
    private final String campGroundId;
}
