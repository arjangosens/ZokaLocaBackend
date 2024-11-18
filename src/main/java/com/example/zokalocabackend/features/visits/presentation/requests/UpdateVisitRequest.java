package com.example.zokalocabackend.features.visits.presentation.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record UpdateVisitRequest(
        @NotNull
        LocalDate arrivalDate,

        @NotNull
        LocalDate departureDate,

        @Min(1)
        @Max(10)
        int rating,

        @Min(1)
        Integer numOfPeople,

        @Min(0)
        Double price,

        String description,
        Set<String> pros,
        Set<String> cons) {
}
