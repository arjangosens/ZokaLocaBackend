package com.example.zokalocabackend.campsites.presentation.responses;

import com.example.zokalocabackend.campsites.domain.SurroundingProximity;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.FacilityDTO;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.PersonLimitDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@SuperBuilder
@Getter
public class GetBuildingResponse extends GetCampsiteResponse{
    private final int numOfRooms;
    private final int numOfCommonAreas;

    public GetBuildingResponse(String id, String name, String description, AddressDTO address, PersonLimitDTO personLimit, CampsitePriceDTO price, String arrivalTime, String departureTime, int numOfToilets, int numOfShowers, int numOfWaterSources, HashMap<String, SurroundingProximity> surroundings, List<FacilityDTO> facilities, String campGroundId, int numOfRooms, int numOfCommonAreas) {
        super(id, name, description, address, personLimit, price, arrivalTime, departureTime, numOfToilets, numOfShowers, numOfWaterSources, surroundings, facilities, campGroundId);
        this.numOfRooms = numOfRooms;
        this.numOfCommonAreas = numOfCommonAreas;
    }

}
