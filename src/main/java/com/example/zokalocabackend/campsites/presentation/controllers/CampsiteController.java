package com.example.zokalocabackend.campsites.presentation.controllers;

import com.example.zokalocabackend.campsites.application.services.CampsiteService;
import com.example.zokalocabackend.campsites.application.services.FacilityService;
import com.example.zokalocabackend.campsites.domain.Campsite;
import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.presentation.mappers.CampsiteMapper;
import com.example.zokalocabackend.campsites.presentation.requests.ModifyCampsiteRequest;
import com.example.zokalocabackend.campsites.presentation.responses.GetCampsiteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/campsites")
public class CampsiteController {
    private final CampsiteService campsiteService;
    private final FacilityService facilityService;

    @Autowired
    public CampsiteController(CampsiteService campsiteService, FacilityService facilityService) {
        this.campsiteService = campsiteService;
        this.facilityService = facilityService;
    }

    @GetMapping
    public ResponseEntity<List<GetCampsiteResponse>> getAllCampsites() {
        List<Campsite> campsites = campsiteService.getAllCampsites();
        List<GetCampsiteResponse> getCampsiteResponses = new ArrayList<>();

        for (Campsite campsite : campsites) {
            getCampsiteResponses.add(CampsiteMapper.toGetCampsiteResponse(campsite));
        }

        return ResponseEntity.ok(getCampsiteResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampsiteResponse> getCampsite(@PathVariable String id) {
        Campsite campsite = campsiteService.getCampsiteById(id);
        return ResponseEntity.ok(CampsiteMapper.toGetCampsiteResponse(campsite));
    }

    @PostMapping
    public ResponseEntity<?> createCampsite(@RequestBody ModifyCampsiteRequest modifyCampsiteRequest) {
        Campsite campsite = CampsiteMapper.toCampsite(modifyCampsiteRequest, extractFacilities(modifyCampsiteRequest));
        campsiteService.createCampsite(campsite);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampsite(@PathVariable String id, @RequestBody ModifyCampsiteRequest modifyCampsiteRequest) {
        Campsite campsite = CampsiteMapper.toCampsite(modifyCampsiteRequest, extractFacilities(modifyCampsiteRequest));
        campsiteService.updateCampsite(id, campsite);
        return ResponseEntity.ok().build();
    }

    private Set<Facility> extractFacilities(ModifyCampsiteRequest modifyCampsiteRequest) {
        Set<Facility> facilities = new HashSet<>();

        for (String facilityId : modifyCampsiteRequest.getFacilityIds()) {
            Facility facility = facilityService.getFacilityById(facilityId);
            facilities.add(facility);
        }

        return facilities;
    }
}
