package com.example.zokalocabackend.features.visits.presentation;

import com.example.zokalocabackend.features.visits.Visit;
import com.example.zokalocabackend.features.visits.presentation.requests.ModifyVisitRequest;

public class VisitMapper {
    public static Visit toVisit(String id, ModifyVisitRequest request) {
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
}
