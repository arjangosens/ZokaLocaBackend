package com.example.zokalocabackend.features.campsites.presentation.datatransferobjects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddressDTO(@NotBlank String country, @NotBlank String city, String street, String houseNumber, String zipcode,
                         Double latitude, Double longitude, @Min(0) int distanceInKm) {
}
