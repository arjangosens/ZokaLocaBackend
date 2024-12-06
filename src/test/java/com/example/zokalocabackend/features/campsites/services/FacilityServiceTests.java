package com.example.zokalocabackend.features.campsites.services;

import com.example.zokalocabackend.BaseTest;
import com.example.zokalocabackend.features.campsites.services.FacilityService;
import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.campsites.persistence.FacilityRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class FacilityServiceTests extends BaseTest {
    @MockBean
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityService facilityService;

    @Test
    public void getFacilityById_withValidId_returnsFacility() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Facility facility = new Facility(id, "Keuken");
        given(this.facilityRepository.findById(id)).willReturn(Optional.of(facility));

        // Act
        Facility result = facilityService.getFacilityById(id);

        // Assert
        assertThat(result).isEqualTo(facility);
    }

    @Test
    public void getFacilityById_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.facilityRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> facilityService.getFacilityById(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getAllFacilities_returnsFacilities() {
        // Arrange
        Facility facility1 = new Facility("67078988be2d215dbf64ea22", "Keuken");
        Facility facility2 = new Facility("67078988be2d215dbf64ea23", "Pionierhout");
        given(this.facilityRepository.findAll()).willReturn(List.of(facility1, facility2));

        // Act
        List<Facility> result = facilityService.getAllFacilities();

        // Assert
        assertThat(result).containsExactlyInAnyOrder(facility1, facility2);
    }

    @Test
    public void createFacility_withValidName_createsFacility() {
        // Arrange
        String name = "Keuken";
        given(this.facilityRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act
        facilityService.createFacility(name);

        // Assert
        verify(this.facilityRepository).save(argThat(f -> f.getName().equals(name)));
    }

    @Test
    public void createFacility_withDuplicateName_throwsException() {
        // Arrange
        String name = "Keuken";
        given(this.facilityRepository.existsByNameIgnoreCase(name)).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> facilityService.createFacility(name))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void createFacility_withInvalidName_throwsException() {
        // Arrange
        String name = "";
        given(this.facilityRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> facilityService.createFacility(name))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateFacility_withValidIdAndName_updatesFacility() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "Keuken";
        Facility facility = new Facility(id, name);
        given(this.facilityRepository.findById(id)).willReturn(Optional.of(facility));
        given(this.facilityRepository.findByNameIgnoreCase(name)).willReturn(null);

        // Act
        facilityService.updateFacility(id, name);

        // Assert
        verify(this.facilityRepository).save(argThat(f -> f.getId().equals(id) && f.getName().equals(name)));
    }

    @Test
    public void updateFacility_withDuplicateName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "Keuken";
        Facility facility = new Facility(id, name);
        given(this.facilityRepository.findById(id)).willReturn(Optional.of(facility));
        given(this.facilityRepository.findByNameIgnoreCase(name)).willReturn(new Facility("67078988be2d215dbf64ea23", name));

        // Act & Assert
        assertThatThrownBy(() -> facilityService.updateFacility(id, name))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void updateFacility_withInvalidName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "";
        Facility facility = new Facility(id, name);
        given(this.facilityRepository.findById(id)).willReturn(Optional.of(facility));

        // Act & Assert
        assertThatThrownBy(() -> facilityService.updateFacility(id, name))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateFacility_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        String name = "Keuken";
        given(this.facilityRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> facilityService.updateFacility(id, name))
                .isInstanceOf(NoSuchElementException.class);
    }
}
