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
public class GetBuildingResponse extends GetCampsiteResponse{
    private final int numOfRooms;
    private final int numOfCommonAreas;

    public GetBuildingResponse(String id, String name, String description, AddressDTO address, PersonLimitDTO personLimit, CampsitePriceDTO price, String arrivalTime, String departureTime, int numOfToilets, int numOfShowers, int numOfWaterSources, HashMap<String, SurroundingProximity> surroundings, HashMap<String, String> externalSources, List<FacilityDTO> facilities, String campGroundId, int numOfRooms, int numOfCommonAreas, String thumbnailId, List<String> imageIds) {
        super(id, name, "BUILDING", description, address, personLimit, price, arrivalTime, departureTime, numOfToilets, numOfShowers, numOfWaterSources, surroundings, externalSources, facilities, campGroundId, 0, thumbnailId, imageIds);
        this.numOfRooms = numOfRooms;
        this.numOfCommonAreas = numOfCommonAreas;
    }

}
