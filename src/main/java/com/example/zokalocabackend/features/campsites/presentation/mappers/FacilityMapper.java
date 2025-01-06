package com.example.zokalocabackend.features.campsites.presentation.mappers;

import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.FacilityDTO;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FacilityMapper {
    public static FacilityDTO toGetFacilityResponse(Facility facility) {
        return new FacilityDTO(facility.getId(), facility.getName());
    }

    public static List<FacilityDTO> toGetFacilityResponsesList(List<Facility> facilities) {
        List<FacilityDTO> facilityResponses = new ArrayList<>();

        for (Facility facility : facilities) {
            facilityResponses.add(toGetFacilityResponse(facility));
        }

        return facilityResponses;
    }
}
