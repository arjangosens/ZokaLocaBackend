package com.example.zokalocabackend.application.services;

import com.example.zokalocabackend.campsites.services.CampsiteService;
import com.example.zokalocabackend.campsites.domain.*;
import com.example.zokalocabackend.campsites.persistence.CampsiteRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CampsiteServiceTests {

    @MockBean
    private CampsiteRepository campsiteRepository;

    @Autowired
    private CampsiteService campsiteService;

    @Test
    public void getAllCampsites_returnsCampsites() {
        // Arrange
        Campsite campsite1 = Building.builder()
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();

        Campsite campsite2 = Field.builder()
                .name("Het veld")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 3.50))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfToilets(1)
                .numOfShowers(1)
                .numOfWaterSources(1)
                .build();

        Pageable pageable = Pageable.ofSize(10).withPage(0);
        CampsiteFilter filter = new CampsiteFilter();
        given(this.campsiteRepository.findAllWithFilters(pageable, filter)).willReturn(new PageImpl<>(List.of(campsite1, campsite2)));

        // Act
        Page<Campsite> result = campsiteService.getAllCampsites(pageable, filter);

        // Assert
        assertThat(result.getContent()).containsExactlyInAnyOrder(campsite1, campsite2);
    }

    @Test
    public void getCampsiteById_withValidId_returnsCampsite() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Campsite campsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        given(this.campsiteRepository.findById(id)).willReturn(java.util.Optional.of(campsite));

        // Act
        Campsite result = campsiteService.getCampsiteById(id);

        // Assert
        assertThat(result).isEqualTo(campsite);
    }

    @Test
    public void getCampsiteById_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.campsiteRepository.findById(id)).willReturn(java.util.Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> campsiteService.getCampsiteById(id))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    @Test
    public void createCampsite_withValidCampsite_createsCampsite() {
        // Arrange
        Campsite campsite = Building.builder()
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        given(this.campsiteRepository.existsByNameIgnoreCase(campsite.getName())).willReturn(false);

        // Act
        campsiteService.createCampsite(campsite);

        // Assert
        verify(this.campsiteRepository).save(argThat(c -> c.getName().equals(campsite.getName())));
    }

    @Test
    public void createCampsite_withDuplicateName_throwsException() {
        // Arrange
        Campsite campsite = Building.builder()
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        given(this.campsiteRepository.existsByNameIgnoreCase(campsite.getName())).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> campsiteService.createCampsite(campsite))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void createCampsite_withInvalidCampsite_throwsException() {
        // Arrange
        Campsite campsite = Building.builder()
                .name("")
                .price(new CampsitePrice(null, -1.00))
                .address(new Address("NL", "", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(-5)
                .numOfCommonAreas(-1)
                .numOfToilets(-5)
                .numOfShowers(-1)
                .numOfWaterSources(-10)
                .build();
        given(this.campsiteRepository.existsByNameIgnoreCase(campsite.getName())).willReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> campsiteService.createCampsite(campsite))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateCampsite_withValidIdAndCampsite_updatesCampsite() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Campsite campsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        Campsite updatedCampsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        given(this.campsiteRepository.findById(id)).willReturn(java.util.Optional.of(campsite));
        given(this.campsiteRepository.findByNameIgnoreCase(campsite.getName())).willReturn(null);

        // Act
        campsiteService.updateCampsite(id, updatedCampsite);

        // Assert
        verify(this.campsiteRepository).save(argThat(c -> c.getId().equals(id) && c.getName().equals(updatedCampsite.getName())));
    }

    @Test
    public void updateCampsite_withDuplicateName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Campsite campsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();

        Campsite updatedCampsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();

        Building foundBuilding = Building.builder()
                .id("67078988be2d215dbf64ea23")
                .name(updatedCampsite.getName())
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();
        given(this.campsiteRepository.findById(id)).willReturn(java.util.Optional.of(campsite));
        given(this.campsiteRepository.findByNameIgnoreCase(updatedCampsite.getName())).willReturn(foundBuilding);

        // Act & Assert
        assertThatThrownBy(() -> campsiteService.updateCampsite(id, updatedCampsite))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void updateCampsite_withInvalidCampsite_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Campsite campsite = Building.builder()
                .id(id)
                .name("Het gebouw")
                .price(new CampsitePrice(PriceMode.PER_PERSON, 5.00))
                .address(new Address("NL", "Tilburg", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(5)
                .numOfCommonAreas(2)
                .numOfToilets(3)
                .numOfShowers(2)
                .numOfWaterSources(1)
                .build();

        Campsite updatedCampsite = Building.builder()
                .id(id)
                .name("")
                .price(new CampsitePrice(null, -1.00))
                .address(new Address("NL", "", "Straatlaan", "1", "1234AB", null, null, 15))
                .numOfRooms(-5)
                .numOfCommonAreas(-1)
                .numOfToilets(-5)
                .numOfShowers(-1)
                .numOfWaterSources(-10)
                .build();
        given(this.campsiteRepository.findById(id)).willReturn(java.util.Optional.of(campsite));

        // Act & Assert
        assertThatThrownBy(() -> campsiteService.updateCampsite(id, updatedCampsite))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
