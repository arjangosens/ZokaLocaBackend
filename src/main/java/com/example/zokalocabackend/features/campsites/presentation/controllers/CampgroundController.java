package com.example.zokalocabackend.features.campsites.presentation.controllers;

import com.example.zokalocabackend.features.campsites.services.CampgroundService;
import com.example.zokalocabackend.features.campsites.domain.Campground;
import com.example.zokalocabackend.features.campsites.presentation.mappers.CampgroundMapper;
import com.example.zokalocabackend.features.campsites.presentation.requests.ModifyCampgroundRequest;
import com.example.zokalocabackend.features.campsites.presentation.responses.GetCampgroundResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campgrounds")
public class CampgroundController {
    private final CampgroundService campgroundService;

    @Autowired
    public CampgroundController(CampgroundService campgroundService) {
        this.campgroundService = campgroundService;
    }

    @GetMapping
    public ResponseEntity<List<GetCampgroundResponse>> getAllCampgrounds() {
        List<Campground> campgrounds = campgroundService.getAllCampgrounds();
        return ResponseEntity.ok(CampgroundMapper.toGetCampgroundResponsesList(campgrounds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampgroundResponse> getCampground(@PathVariable String id) {
        Campground campground = campgroundService.getCampgroundById(id);
        return ResponseEntity.ok(CampgroundMapper.toGetCampgroundResponse(campground));
    }

    @PostMapping
    public ResponseEntity<?> createCampground(@Valid @RequestBody ModifyCampgroundRequest request) {
        campgroundService.createCampground(request.name());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampground(@PathVariable String id, @Valid @RequestBody ModifyCampgroundRequest request) {
        campgroundService.updateCampground(id, request.name());
        return ResponseEntity.ok().build();
    }
}
