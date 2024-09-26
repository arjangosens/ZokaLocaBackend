package com.example.zokalocabackend.campsites.presentation.mappers;

import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.presentation.responses.GetFacilityResponse;

import java.util.ArrayList;
import java.util.List;

public class FacilityMapper {
    public static GetFacilityResponse toGetFacilityResponse(Facility facility) {
        return new GetFacilityResponse(facility.getId(), facility.getName());
    }

    public static List<GetFacilityResponse> toGetFacilityResponsesList(List<Facility> facilities) {
        List<GetFacilityResponse> facilityResponses = new ArrayList<>();

        for (Facility facility : facilities) {
            facilityResponses.add(toGetFacilityResponse(facility));
        }

        return facilityResponses;
    }
}
