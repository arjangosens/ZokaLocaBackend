package com.example.zokalocabackend.features.campsites.presentation.mappers;

import com.example.zokalocabackend.features.campsites.domain.*;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.AddressDTO;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.CampsitePriceDTO;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.PersonLimitDTO;
import com.example.zokalocabackend.features.campsites.presentation.requests.GetAllCampsitesRequest;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyBuildingRequest;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyCampsiteRequest;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyFieldRequest;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetBuildingResponse;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampsiteResponse;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetFieldResponse;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class CampsiteMapper {

    public static Campsite toCampsite(ModifyCampsiteRequest request, Set<Facility> facilities) {
        if (request instanceof ModifyBuildingRequest buildingRequest) {
            return Building.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .address(toAddress(request.getAddress()))
                    .personLimit(toPersonLimit(request.getPersonLimit()))
                    .price(toCampsitePrice(request.getPrice()))
                    .arrivalTime(request.getArrivalTime())
                    .departureTime(request.getDepartureTime())
                    .numOfToilets(request.getNumOfToilets())
                    .numOfShowers(request.getNumOfShowers())
                    .numOfWaterSources(request.getNumOfWaterSources())
                    .surroundings(request.getSurroundings())
                    .externalSources(buildingRequest.getExternalSources())
                    .facilities(facilities)
                    .campGroundId(request.getCampGroundId())
                    .numOfRooms(buildingRequest.getNumOfRooms())
                    .numOfCommonAreas(buildingRequest.getNumOfCommonAreas())
                    .build();
        } else if (request instanceof ModifyFieldRequest fieldRequest) {
            return Field.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .address(toAddress(request.getAddress()))
                    .personLimit(toPersonLimit(request.getPersonLimit()))
                    .price(toCampsitePrice(request.getPrice()))
                    .arrivalTime(request.getArrivalTime())
                    .departureTime(request.getDepartureTime())
                    .numOfToilets(request.getNumOfToilets())
                    .numOfShowers(request.getNumOfShowers())
                    .numOfWaterSources(request.getNumOfWaterSources())
                    .surroundings(request.getSurroundings())
                    .externalSources(fieldRequest.getExternalSources())
                    .facilities(facilities)
                    .campGroundId(request.getCampGroundId())
                    .sizeSquareMeters(fieldRequest.getSizeSquareMeters())
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown campsite type");
        }
    }

    public static GetCampsiteResponse toGetCampsiteResponse(Campsite campsite, double rating) {
        if (campsite instanceof Building building) {
            return GetBuildingResponse.builder()
                    .id(campsite.getId())
                    .name(campsite.getName())
                    .campsiteType("BUILDING")
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
                    .externalSources(building.getExternalSources())
                    .facilities(FacilityMapper.toGetFacilityResponsesList(campsite.getFacilities().stream().toList()))
                    .campGroundId(campsite.getCampGroundId())
                    .numOfRooms(building.getNumOfRooms())
                    .numOfCommonAreas(building.getNumOfCommonAreas())
                    .rating(rating)
                    .thumbnailId(campsite.getThumbnailAssetId())
                    .imageIds(campsite.getImageIds().stream().toList())
                    .build();
        } else if (campsite instanceof Field field) {
            return GetFieldResponse.builder()
                    .id(campsite.getId())
                    .name(campsite.getName())
                    .campsiteType("FIELD")
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
                    .externalSources(field.getExternalSources())
                    .facilities(FacilityMapper.toGetFacilityResponsesList(campsite.getFacilities().stream().toList()))
                    .campGroundId(campsite.getCampGroundId())
                    .sizeSquareMeters(field.getSizeSquareMeters())
                    .rating(rating)
                    .thumbnailId(campsite.getThumbnailAssetId())
                    .imageIds(campsite.getImageIds().stream().toList())
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

    public static CampsiteFilter toCampsiteFilter(GetAllCampsitesRequest request) {
        return new CampsiteFilter(request.name(), request.campsiteType(), request.minDistanceInKm(), request.maxDistanceInKm(), request.numOfPeople(), request.facilityIds(), request.minNumOfToilets(), request.minNumOfShowers(), request.minNumOfWaterSources(), request.minNumOfRooms(), request.minNumOfCommonAreas(), request.minSizeSquareMeters());
    }
}
