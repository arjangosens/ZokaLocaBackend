package com.example.zokalocabackend.campsites.presentation.controllers;

import com.example.zokalocabackend.campsites.application.services.FacilityService;
import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.presentation.requests.CreateFacilityRequest;
import com.example.zokalocabackend.campsites.presentation.requests.UpdateFacilityRequest;
import com.example.zokalocabackend.campsites.presentation.responses.GetFacilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public ResponseEntity<List<GetFacilityResponse>> getAllFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
        List<GetFacilityResponse> facilityResponses = new ArrayList<>();

        for (Facility facility : facilities) {
            facilityResponses.add(new GetFacilityResponse(facility.getId(), facility.getName()));
        }

        return ResponseEntity.ok(facilityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFacilityResponse> getFacility(@PathVariable String id) {
        Facility facility = facilityService.getFacilityById(id);
        GetFacilityResponse response = new GetFacilityResponse(facility.getId(), facility.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody CreateFacilityRequest request) {
        ResponseEntity<?> response;
        String name = request.name();

        if (name == null || name.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Facility name cannot be empty");
        } else {
            facilityService.createFacility(name);
            response = ResponseEntity.ok().build();
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable String id, @RequestBody UpdateFacilityRequest request) {
        ResponseEntity<?> response;
        String name = request.name();

        if (name == null || name.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Facility name cannot be empty");
        } else {
            facilityService.updateFacility(id, name);
            response = ResponseEntity.ok().build();
        }

        return response;
    }
}
