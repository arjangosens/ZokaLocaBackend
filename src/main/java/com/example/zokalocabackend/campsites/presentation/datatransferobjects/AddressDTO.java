package com.example.zokalocabackend.campsites.presentation.datatransferobjects;

import lombok.NonNull;

public record AddressDTO(@NonNull String country, @NonNull String city, String street, String houseNumber, String zipcode,
                         String latitude, String longitude, int distanceInKm) {
}
