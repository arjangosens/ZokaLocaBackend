package com.example.zokalocabackend.campsites.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
public class Address {

    @NonNull
    private String country;

    @NonNull
    private String city;

    private String street;
    private String houseNumber;
    private String zipcode;
    private String latitude;
    private String longitude;
    private int distanceInKm;

    public Address(String country, String city, String street, String houseNumber, String zipcode, String latitude, String longitude, int distanceInKm) {
        if (!isValidCountryCode(country)) {
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
        if (!isValidCountryCode(country)) {
            throw new IllegalArgumentException("Invalid country code");
        }

        this.country = country;
    }

    private boolean isValidCountryCode(String countryCode) {
        String[] countryCodes = Locale.getISOCountries();

        for (String code : countryCodes) {
            if (code.equals(countryCode)) {
                return true;
            }
        }

        return false;
    }
}
