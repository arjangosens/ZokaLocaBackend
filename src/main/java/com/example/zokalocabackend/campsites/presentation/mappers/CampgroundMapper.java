package com.example.zokalocabackend.campsites.presentation.mappers;

import com.example.zokalocabackend.campsites.domain.Campground;
import com.example.zokalocabackend.campsites.presentation.responses.GetCampgroundResponse;

import java.util.ArrayList;
import java.util.List;

public class CampgroundMapper {
    public static GetCampgroundResponse toGetCampgroundResponse(Campground campground) {
        return new GetCampgroundResponse(campground.getId(), campground.getName());
    }

    public static List<GetCampgroundResponse> toGetCampgroundResponsesList(List<Campground> campgrounds) {
        List<GetCampgroundResponse> campgroundResponses = new ArrayList<>();

        for (Campground campground : campgrounds) {
            campgroundResponses.add(toGetCampgroundResponse(campground));
        }

        return campgroundResponses;
    }
}