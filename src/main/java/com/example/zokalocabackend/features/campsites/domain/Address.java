package com.example.zokalocabackend.features.campsites.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
public class Address {

    @NonNull
    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    @NonNull
    @NotBlank
    private String city;

    private String street;
    private String houseNumber;
    private String zipcode;
    private Double latitude;
    private Double longitude;

    @Min(0)
    private int distanceInKm;

    public Address(String country, String city, String street, String houseNumber, String zipcode, Double latitude, Double longitude, int distanceInKm) {
        if (isInvalidCountryCode(country)) {
            throw new IllegalArgumentException("Invalid country code");
        }

        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceInKm = distanceInKm;
    }

    public void setCountry(@NonNull String country) {
        if (isInvalidCountryCode(country)) {
            throw new IllegalArgumentException("Invalid country code");
        }

        this.country = country;
    }

    private boolean isInvalidCountryCode(String countryCode) {
        String[] countryCodes = Locale.getISOCountries();

        for (String code : countryCodes) {
            if (code.equals(countryCode)) {
                return false;
            }
        }

        return true;
    }
}
