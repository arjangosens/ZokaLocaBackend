package com.example.zokalocabackend.features.visits.presentation;

import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampsiteResponse;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;
import com.example.zokalocabackend.features.visits.Visit;
import com.example.zokalocabackend.features.visits.presentation.requests.CreateVisitRequest;
import com.example.zokalocabackend.features.visits.presentation.requests.UpdateVisitRequest;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithBranchAndCampsiteResponse;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithBranchResponse;
import com.example.zokalocabackend.features.visits.presentation.responses.GetVisitWithCampsiteResponse;

public class VisitMapper {
    public static Visit toVisit(String id, CreateVisitRequest request) {
        return Visit.builder()
                .id(id)
                .arrivalDate(request.arrivalDate())
                .departureDate(request.departureDate())
                .rating(request.rating())
                .branchId(request.branchId())
                .campsiteId(request.campsiteId())
                .numOfPeople(request.numOfPeople())
                .price(request.price())
                .description(request.description())
                .pros(request.pros())
                .cons(request.cons())
                .build();
    }

    public static Visit toVisit(String id, UpdateVisitRequest request, String branchId, String campsiteId) {
        return Visit.builder()
                .id(id)
                .arrivalDate(request.arrivalDate())
                .departureDate(request.departureDate())
                .rating(request.rating())
                .branchId(branchId)
                .campsiteId(campsiteId)
                .numOfPeople(request.numOfPeople())
                .price(request.price())
                .description(request.description())
                .pros(request.pros())
                .cons(request.cons())
                .build();
    }

    public static GetVisitWithBranchResponse toGetVisitWithBranchResponse(Visit visit, BranchCollectionItemDTO branchDTO) {
        return new GetVisitWithBranchResponse(
                visit.getId(),
                visit.getArrivalDate().toString(),
                visit.getDepartureDate().toString(),
                visit.getRating(),
                visit.getBranchId(),
                visit.getCampsiteId(),
                visit.getNumOfPeople(),
                visit.getPrice(),
                visit.getDescription(),
                visit.getPros().toArray(String[]::new),
                visit.getCons().toArray(String[]::new),
                branchDTO
        );
    }

    public static GetVisitWithCampsiteResponse toGetVisitWithCampsiteResponse(Visit visit, GetCampsiteResponse campsiteDTO) {
        return new GetVisitWithCampsiteResponse(
                visit.getId(),
                visit.getArrivalDate().toString(),
                visit.getDepartureDate().toString(),
                visit.getRating(),
                visit.getBranchId(),
                visit.getCampsiteId(),
                visit.getNumOfPeople(),
                visit.getPrice(),
                visit.getDescription(),
                visit.getPros().toArray(String[]::new),
                visit.getCons().toArray(String[]::new),
                campsiteDTO
        );
    }

    public static GetVisitWithBranchAndCampsiteResponse toGetVisitWithBranchAndCampsiteResponse(Visit visit, BranchCollectionItemDTO branchDTO, GetCampsiteResponse campsiteDTO) {
        return new GetVisitWithBranchAndCampsiteResponse(
                visit.getId(),
                visit.getArrivalDate().toString(),
                visit.getDepartureDate().toString(),
                visit.getRating(),
                visit.getBranchId(),
                visit.getCampsiteId(),
                visit.getNumOfPeople(),
                visit.getPrice(),
                visit.getDescription(),
                visit.getPros().toArray(String[]::new),
                visit.getCons().toArray(String[]::new),
                branchDTO,
                campsiteDTO
        );
    }
}
