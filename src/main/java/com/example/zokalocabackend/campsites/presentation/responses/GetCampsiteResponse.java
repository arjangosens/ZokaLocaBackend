package com.example.zokalocabackend.campsites.presentation.responses;

import com.example.zokalocabackend.campsites.domain.SurroundingProximity;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.FacilityDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.PersonLimitDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@SuperBuilder
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "campsiteType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetBuildingResponse.class, name = "BUILDING"),
        @JsonSubTypes.Type(value = GetFieldResponse.class, name = "FIELD")
})
public abstract class GetCampsiteResponse {
    private final String id;
    private final String name;
    private final String campsiteType;
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
    private final HashMap<String, String> externalSources;
    private final List<FacilityDTO> facilities;
    private final String campGroundId;
}
