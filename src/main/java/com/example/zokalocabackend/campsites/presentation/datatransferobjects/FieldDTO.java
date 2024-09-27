package com.example.zokalocabackend.campsites.presentation.datatransferobjects;

import com.example.zokalocabackend.campsites.domain.SurroundingProximity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@SuperBuilder
@Getter
public class FieldDTO extends CampsiteDTO {
    private final int sizeSquareMeters;

    public FieldDTO(String id, String name, String description, AddressDTO address, PersonLimitDTO personLimit, CampsitePriceDTO price, String arrivalTime, String departureTime, int numOfToilets, int numOfShowers, int numOfWaterSources, HashMap<String, SurroundingProximity> surroundings, List<String> facilityIds, String campGroundId, int sizeSquareMeters) {
        super(id, name, description, address, personLimit, price, arrivalTime, departureTime, numOfToilets, numOfShowers, numOfWaterSources, surroundings, facilityIds, campGroundId);
        this.sizeSquareMeters = sizeSquareMeters;
    }

}
