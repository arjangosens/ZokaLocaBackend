package com.example.zokalocabackend.campsites.presentation.datatransferobjects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddressDTO(@NotBlank String country, @NotBlank String city, String street, String houseNumber, String zipcode,
                         String latitude, String longitude, @Min(0) int distanceInKm) {
}
