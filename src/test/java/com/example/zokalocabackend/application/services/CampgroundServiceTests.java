package com.example.zokalocabackend.application.services;

import com.example.zokalocabackend.campsites.application.services.CampgroundService;
import com.example.zokalocabackend.campsites.domain.Campground;
import com.example.zokalocabackend.campsites.persistence.CampgroundRepository;
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

@SpringBootTest
public class CampgroundServiceTests {

    @MockBean
    private CampgroundRepository campgroundRepository;

    @Autowired
    private CampgroundService campgroundService;

    @Test
    public void getCampgroundById_withValidId_returnsCampground() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Campground campground = new Campground(id, "Labelterrein 1");
        given(this.campgroundRepository.findById(id)).willReturn(Optional.of(campground));

        // Act
        Campground result = campgroundService.getCampgroundById(id);

        // Assert
        assertThat(result).isEqualTo(campground);
    }

    @Test
    public void getCampgroundById_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.campgroundRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> campgroundService.getCampgroundById(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getAllCampgrounds_returnsCampgrounds() {
        // Arrange
        Campground campground1 = new Campground("67078988be2d215dbf64ea22", "Labelterrein 1");
        Campground campground2 = new Campground("67078988be2d215dbf64ea23", "Labelterrein 2");
        given(this.campgroundRepository.findAll()).willReturn(List.of(campground1, campground2));

        // Act
        List<Campground> result = campgroundService.getAllCampgrounds();

        // Assert
        assertThat(result).containsExactlyInAnyOrder(campground1, campground2);
    }

    @Test
    public void createCampground_withValidName_createsCampground() {
        // Arrange
        String name = "Labelterrein 1";
        given(this.campgroundRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act
        campgroundService.createCampground(name);

        // Assert
        verify(campgroundRepository).save(argThat(campground -> campground.getName().equals(name)));
    }

    @Test
    public void createCampground_withDuplicateName_throwsException() {
        // Arrange
        String name = "Labelterrein 1";
        given(this.campgroundRepository.existsByNameIgnoreCase(name)).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> campgroundService.createCampground(name))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void createCampground_withInvalidName_throwsException() {
        // Arrange
        String name = "";
        given(this.campgroundRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> campgroundService.createCampground(name))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateCampground_withValidName_updatesCampground() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "Labelterrein 2";
        Campground campground = new Campground(id, "Labelterrein 1");
        given(this.campgroundRepository.findById(id)).willReturn(Optional.of(campground));
        given(this.campgroundRepository.findByNameIgnoreCase(name)).willReturn(null);

        // Act
        campgroundService.updateCampground(id, name);

        // Assert
        verify(campgroundRepository).save(argThat(c -> c.getName().equals(name)));
    }

    @Test
    public void updateCampground_withDuplicateName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "Labelterrein 2";
        Campground campground = new Campground(id, "Labelterrein 1");
        given(this.campgroundRepository.findById(id)).willReturn(Optional.of(campground));
        given(this.campgroundRepository.findByNameIgnoreCase(name)).willReturn(new Campground("67078988be2d215dbf64ea23", name));

        // Act & Assert
        assertThatThrownBy(() -> campgroundService.updateCampground(id, name))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void updateCampground_withInvalidName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        String name = "";
        Campground campground = new Campground(id, "Labelterrein 1");
        given(this.campgroundRepository.findById(id)).willReturn(Optional.of(campground));

        // Act & Assert
        assertThatThrownBy(() -> campgroundService.updateCampground(id, name))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
