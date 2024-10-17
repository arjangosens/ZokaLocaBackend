package com.example.zokalocabackend.campsites.presentation.requests;

import com.example.zokalocabackend.campsites.domain.SurroundingProximity;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.PersonLimitDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
        @JsonSubTypes.Type(value = ModifyBuildingRequest.class, name = "BUILDING"),
        @JsonSubTypes.Type(value = ModifyFieldRequest.class, name = "FIELD")
})
public abstract class ModifyCampsiteRequest {
    private final String id;

    @NotBlank
    private final String name;

    private final String description;

    @Valid
    private final AddressDTO address;

    @Valid
    private final PersonLimitDTO personLimit;

    @Valid
    private final CampsitePriceDTO price;

    private final String arrivalTime;
    private final String departureTime;

    @Min(0)
    private final int numOfToilets;

    @Min(0)
    private final int numOfShowers;

    @Min(0)
    private final int numOfWaterSources;

    private final HashMap<String, SurroundingProximity> surroundings;
    private final HashMap<String, String> externalSources;
    private final List<String> facilityIds;
    private final String campGroundId;
}
