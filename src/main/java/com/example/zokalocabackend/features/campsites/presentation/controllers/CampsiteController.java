package com.example.zokalocabackend.features.campsites.presentation.controllers;

import com.example.zokalocabackend.features.campsites.services.CampsiteService;
import com.example.zokalocabackend.features.campsites.services.FacilityService;
import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.campsites.domain.CampsiteFilter;
import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.campsites.presentation.mappers.CampsiteMapper;
import com.example.zokalocabackend.features.campsites.presentation.requests.GetAllCampsitesRequest;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyCampsiteRequest;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampsiteResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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
    public ResponseEntity<Page<GetCampsiteResponse>> getAllCampsites(GetAllCampsitesRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.sortOrder()), request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.pageSize(), sort);
        CampsiteFilter filter = CampsiteMapper.toCampsiteFilter(request);

        Page<Campsite> campsites = campsiteService.getAllCampsites(pageable, filter);
        Page<GetCampsiteResponse> getCampsiteResponses = campsites.map(CampsiteMapper::toGetCampsiteResponse);

        return ResponseEntity.ok(getCampsiteResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampsiteResponse> getCampsite(@PathVariable String id) {
        Campsite campsite = campsiteService.getCampsiteById(id);
        return ResponseEntity.ok(CampsiteMapper.toGetCampsiteResponse(campsite));
    }

    @PostMapping
    public ResponseEntity<?> createCampsite(@Valid @RequestBody ModifyCampsiteRequest modifyCampsiteRequest) {
        Campsite campsite = CampsiteMapper.toCampsite(modifyCampsiteRequest, extractFacilities(modifyCampsiteRequest));
        campsiteService.createCampsite(campsite);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampsite(@PathVariable String id, @Valid @RequestBody ModifyCampsiteRequest modifyCampsiteRequest) {
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
