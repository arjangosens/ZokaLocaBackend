package com.example.zokalocabackend.campsites.presentation.controllers;

import com.example.zokalocabackend.campsites.presentation.mappers.FacilityMapper;
import com.example.zokalocabackend.campsites.application.services.FacilityService;
import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.presentation.requests.ModifyFacilityRequest;
import com.example.zokalocabackend.campsites.presentation.responses.GetFacilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(FacilityMapper.toGetFacilityResponsesList(facilities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFacilityResponse> getFacility(@PathVariable String id) {
        Facility facility = facilityService.getFacilityById(id);
        return ResponseEntity.ok(FacilityMapper.toGetFacilityResponse(facility));
    }

    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody ModifyFacilityRequest request) {
        String name = request.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Facility name cannot be empty");
        }

        facilityService.createFacility(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable String id, @RequestBody ModifyFacilityRequest request) {
        String name = request.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Facility name cannot be empty");
        }

        facilityService.updateFacility(id, name);
        return ResponseEntity.ok().build();
    }
}