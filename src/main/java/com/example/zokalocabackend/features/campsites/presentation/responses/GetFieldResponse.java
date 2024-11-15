package com.example.zokalocabackend.features.campsites.presentation.responses;

import com.example.zokalocabackend.features.campsites.domain.SurroundingProximity;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.FacilityDTO;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.PersonLimitDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@SuperBuilder
@Getter
public class GetFieldResponse extends GetCampsiteResponse{
    private final int sizeSquareMeters;

    public GetFieldResponse(String id, String name, String description, AddressDTO address, PersonLimitDTO personLimit, CampsitePriceDTO price, String arrivalTime, String departureTime, int numOfToilets, int numOfShowers, int numOfWaterSources, HashMap<String, SurroundingProximity> surroundings, HashMap<String, String> externalSources, List<FacilityDTO> facilities, String campGroundId, int sizeSquareMeters) {
        super(id, name, "FIELD", description, address, personLimit, price, arrivalTime, departureTime, numOfToilets, numOfShowers, numOfWaterSources, surroundings, externalSources, facilities, campGroundId);
        this.sizeSquareMeters = sizeSquareMeters;
    }
}
