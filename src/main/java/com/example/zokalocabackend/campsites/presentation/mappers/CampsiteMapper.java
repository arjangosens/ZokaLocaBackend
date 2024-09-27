package com.example.zokalocabackend.campsites.presentation.mappers;

import com.example.zokalocabackend.campsites.domain.*;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.*;

import java.util.List;
import java.util.Set;

public class CampsiteMapper {
    public static CampsiteDTO toCampsiteDTO(Campsite campsite) {
        List<String> facilities = campsite.getFacilities().stream().map(Facility::getId).toList();
        if (campsite instanceof Building building) {
            return BuildingDTO.builder()
                    .id(campsite.getId())
                    .name(campsite.getName())
                    .description(campsite.getDescription())
                    .address(toAddressDTO(campsite.getAddress()))
                    .personLimit(toPersonLimitDTO(campsite.getPersonLimit()))
                    .price(toCampsitePriceDTO(campsite.getPrice()))
                    .arrivalTime(campsite.getArrivalTime())
                    .departureTime(campsite.getDepartureTime())
                    .numOfToilets(campsite.getNumOfToilets())
                    .numOfShowers(campsite.getNumOfShowers())
                    .numOfWaterSources(campsite.getNumOfWaterSources())
                    .surroundings(campsite.getSurroundings())
                    .facilityIds(facilities)
                    .campGroundId(campsite.getCampGroundId())
                    .numOfRooms(building.getNumOfRooms())
                    .numOfCommonAreas(building.getNumOfCommonAreas())
                    .build();
        } else if (campsite instanceof Field field) {
            return FieldDTO.builder()
                    .id(campsite.getId())
                    .name(campsite.getName())
                    .description(campsite.getDescription())
                    .address(toAddressDTO(campsite.getAddress()))
                    .personLimit(toPersonLimitDTO(campsite.getPersonLimit()))
                    .price(toCampsitePriceDTO(campsite.getPrice()))
                    .arrivalTime(campsite.getArrivalTime())
                    .departureTime(campsite.getDepartureTime())
                    .numOfToilets(campsite.getNumOfToilets())
                    .numOfShowers(campsite.getNumOfShowers())
                    .numOfWaterSources(campsite.getNumOfWaterSources())
                    .surroundings(campsite.getSurroundings())
                    .facilityIds(facilities)
                    .campGroundId(campsite.getCampGroundId())
                    .sizeSquareMeters(field.getSizeSquareMeters())
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown campsite type");
        }
    }

    public static Campsite toCampsite(CampsiteDTO dto, Set<Facility> facilities) {
        if (dto instanceof BuildingDTO buildingDTO) {
            return Building.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .address(toAddress(dto.getAddress()))
                    .personLimit(toPersonLimit(dto.getPersonLimit()))
                    .price(toCampsitePrice(dto.getPrice()))
                    .arrivalTime(dto.getArrivalTime())
                    .departureTime(dto.getDepartureTime())
                    .numOfToilets(dto.getNumOfToilets())
                    .numOfShowers(dto.getNumOfShowers())
                    .numOfWaterSources(dto.getNumOfWaterSources())
                    .surroundings(dto.getSurroundings())
                    .facilities(facilities)
                    .campGroundId(dto.getCampGroundId())
                    .numOfRooms(buildingDTO.getNumOfRooms())
                    .numOfCommonAreas(buildingDTO.getNumOfCommonAreas())
                    .build();
        } else if (dto instanceof FieldDTO fieldDTO) {
            return Field.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .address(toAddress(dto.getAddress()))
                    .personLimit(toPersonLimit(dto.getPersonLimit()))
                    .price(toCampsitePrice(dto.getPrice()))
                    .arrivalTime(dto.getArrivalTime())
                    .departureTime(dto.getDepartureTime())
                    .numOfToilets(dto.getNumOfToilets())
                    .numOfShowers(dto.getNumOfShowers())
                    .numOfWaterSources(dto.getNumOfWaterSources())
                    .surroundings(dto.getSurroundings())
                    .facilities(facilities)
                    .campGroundId(dto.getCampGroundId())
                    .sizeSquareMeters(fieldDTO.getSizeSquareMeters())
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown campsite type");
        }
    }

    public static AddressDTO toAddressDTO(Address address) {
        return new AddressDTO(address.getCountry(), address.getCity(), address.getStreet(), address.getHouseNumber(), address.getZipcode(), address.getLatitude(), address.getLongitude(), address.getDistanceInKm());
    }

    public static Address toAddress(AddressDTO dto) {
        return new Address(dto.country(), dto.city(), dto.street(), dto.houseNumber(), dto.zipcode(), dto.latitude(), dto.longitude(), dto.distanceInKm());
    }

    public static PersonLimitDTO toPersonLimitDTO(PersonLimit personLimit) {
        return new PersonLimitDTO(personLimit.getMinimum(), personLimit.getMaximum());
    }

    public static PersonLimit toPersonLimit(PersonLimitDTO dto) {
        return new PersonLimit(dto.minimum(), dto.maximum());
    }

    public static CampsitePriceDTO toCampsitePriceDTO(CampsitePrice campsitePrice) {
        return new CampsitePriceDTO(campsitePrice.getPriceMode(), campsitePrice.getAmount());
    }

    public static CampsitePrice toCampsitePrice(CampsitePriceDTO dto) {
        return new CampsitePrice(dto.priceMode(), dto.amount());
    }
}
