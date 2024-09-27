package com.example.zokalocabackend.campsites.presentation.controllers;

import com.example.zokalocabackend.campsites.application.services.CampsiteService;
import com.example.zokalocabackend.campsites.application.services.FacilityService;
import com.example.zokalocabackend.campsites.domain.Campsite;
import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.presentation.datatransferobjects.CampsiteDTO;
import com.example.zokalocabackend.campsites.presentation.mappers.CampsiteMapper;
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
    public ResponseEntity<List<CampsiteDTO>> getAllCampsites() {
        List<Campsite> campsites = campsiteService.getAllCampsites();
        List<CampsiteDTO> campsiteDTOS = new ArrayList<>();

        for (Campsite campsite : campsites) {
            campsiteDTOS.add(CampsiteMapper.toCampsiteDTO(campsite));
        }

        return ResponseEntity.ok(campsiteDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampsiteDTO> getCampsite(@PathVariable String id) {
        Campsite campsite = campsiteService.getCampsiteById(id);
        return ResponseEntity.ok(CampsiteMapper.toCampsiteDTO(campsite));
    }

    @PostMapping
    public ResponseEntity<?> createCampsite(@RequestBody CampsiteDTO campsiteDTO) {
        Campsite campsite = CampsiteMapper.toCampsite(campsiteDTO, extractFacilitiesFromDTO(campsiteDTO));
        campsiteService.createCampsite(campsite);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampsite(@PathVariable String id, @RequestBody CampsiteDTO campsiteDTO) {
        Campsite campsite = CampsiteMapper.toCampsite(campsiteDTO, extractFacilitiesFromDTO(campsiteDTO));
        campsiteService.updateCampsite(id, campsite);
        return ResponseEntity.ok().build();
    }

    private Set<Facility> extractFacilitiesFromDTO(CampsiteDTO campsiteDTO) {
        Set<Facility> facilities = new HashSet<>();

        for (String facilityId : campsiteDTO.getFacilityIds()) {
            Facility facility = facilityService.getFacilityById(facilityId);
            facilities.add(facility);
        }

        return facilities;
    }
}
