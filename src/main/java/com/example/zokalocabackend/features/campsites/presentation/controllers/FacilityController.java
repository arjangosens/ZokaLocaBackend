package com.example.zokalocabackend.features.campsites.presentation.controllers;

import com.example.zokalocabackend.features.campsites.services.FacilityService;
import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.campsites.presentation.datatransferobjects.FacilityDTO;
import com.example.zokalocabackend.features.campsites.presentation.mappers.FacilityMapper;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyFacilityRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<FacilityDTO>> getAllFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
        return ResponseEntity.ok(FacilityMapper.toGetFacilityResponsesList(facilities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getFacility(@PathVariable String id) {
        Facility facility = facilityService.getFacilityById(id);
        return ResponseEntity.ok(FacilityMapper.toGetFacilityResponse(facility));
    }

    @PostMapping
    public ResponseEntity<?> createFacility(@Valid @RequestBody ModifyFacilityRequest request) {

        facilityService.createFacility(request.name());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable String id, @Valid @RequestBody ModifyFacilityRequest request) {
        facilityService.updateFacility(id, request.name());
        return ResponseEntity.ok().build();
    }
}