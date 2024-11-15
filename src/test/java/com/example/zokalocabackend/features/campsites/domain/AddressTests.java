package com.example.zokalocabackend.features.campsites.domain;

import com.example.zokalocabackend.features.campsites.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AddressTests {

    @Test
    public void constructor_withInvalidCountryCode_throwsException() {
        // Arrange
        String invalidCountryCode = "INVALID";
        String city = "city";
        String street = "street";
        String houseNumber = "houseNumber";
        String zipcode = "zipcode";
        Double latitude = null;
        Double longitude = null;
        int distanceInKm = 0;

        // Act & Assert
        assertThatThrownBy(() -> new Address(invalidCountryCode, city, street, houseNumber, zipcode, latitude, longitude, distanceInKm))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid country code");
    }

    @Test
    public void constructor_withValidAddress_createsAddress() {
        // Arrange
        String country = "NL";
        String city = "city";
        String street = "street";
        String houseNumber = "houseNumber";
        String zipcode = "zipcode";
        Double latitude = null;
        Double longitude = null;
        int distanceInKm = 0;

        // Act
        Address address = new Address(country, city, street, houseNumber, zipcode, latitude, longitude, distanceInKm);

        // Assert
        assertThat(address.getCountry()).isEqualTo(country);
        assertThat(address.getCity()).isEqualTo(city);
        assertThat(address.getStreet()).isEqualTo(street);
        assertThat(address.getHouseNumber()).isEqualTo(houseNumber);
        assertThat(address.getZipcode()).isEqualTo(zipcode);
        assertThat(address.getLatitude()).isEqualTo(latitude);
        assertThat(address.getLongitude()).isEqualTo(longitude);
        assertThat(address.getDistanceInKm()).isEqualTo(distanceInKm);
    }

    @Test
    public void setCountry_withInvalidCountryCode_throwsException() {
        // Arrange
        String invalidCountryCode = "INVALID";
        String city = "city";
        String street = "street";
        String houseNumber = "houseNumber";
        String zipcode = "zipcode";
        Double latitude = null;
        Double longitude = null;
        int distanceInKm = 0;
        Address address = new Address("NL", city, street, houseNumber, zipcode, latitude, longitude, distanceInKm);

        // Act & Assert
        assertThatThrownBy(() -> address.setCountry(invalidCountryCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid country code");
    }

    @Test
    public void setCountry_withValidCountryCode_setsCountry() {
        // Arrange
        String country = "BE";
        String city = "city";
        String street = "street";
        String houseNumber = "houseNumber";
        String zipcode = "zipcode";
        Double latitude = null;
        Double longitude = null;
        int distanceInKm = 0;
        Address address = new Address("NL", city, street, houseNumber, zipcode, latitude, longitude, distanceInKm);

        // Act
        address.setCountry(country);

        // Assert
        assertThat(address.getCountry()).isEqualTo(country);
    }
}
