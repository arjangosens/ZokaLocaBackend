package com.example.zokalocabackend.campsites.presentation.requests;

import com.example.zokalocabackend.campsites.domain.SurroundingProximity;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.PersonLimitDTO;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@SuperBuilder
@Getter
public class ModifyBuildingRequest extends ModifyCampsiteRequest{

    @Min(0)
    private final int numOfRooms;

    @Min(0)
    private final int numOfCommonAreas;

    public ModifyBuildingRequest(String id, String name, String description, AddressDTO address, PersonLimitDTO personLimit, CampsitePriceDTO price, String arrivalTime, String departureTime, int numOfToilets, int numOfShowers, int numOfWaterSources, HashMap<String, SurroundingProximity> surroundings, HashMap<String, String> externalSources, List<String> facilityIds, String campGroundId, int numOfRooms, int numOfCommonAreas) {
        super(id, name, description, address, personLimit, price, arrivalTime, departureTime, numOfToilets, numOfShowers, numOfWaterSources, surroundings, externalSources, facilityIds, campGroundId);
        this.numOfRooms = numOfRooms;
        this.numOfCommonAreas = numOfCommonAreas;
    }
}
