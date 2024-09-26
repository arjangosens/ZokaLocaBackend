package com.example.zokalocabackend.campsites.presentation.controllers;

import com.example.zokalocabackend.campsites.presentation.mappers.CampgroundMapper;
import com.example.zokalocabackend.campsites.application.services.CampgroundService;
import com.example.zokalocabackend.campsites.domain.Campground;
import com.example.zokalocabackend.campsites.presentation.requests.ModifyCampgroundRequest;
import com.example.zokalocabackend.campsites.presentation.responses.GetCampgroundResponse;
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
    public ResponseEntity<List<GetCampgroundResponse>> GetAllCampgrounds() {
        List<Campground> campgrounds = campgroundService.getAllCampgrounds();
        return ResponseEntity.ok(CampgroundMapper.toGetCampgroundResponsesList(campgrounds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCampgroundResponse> GetCampground(@PathVariable String id) {
        Campground campground = campgroundService.getCampgroundById(id);
        return ResponseEntity.ok(CampgroundMapper.toGetCampgroundResponse(campground));
    }

    @PostMapping
    public ResponseEntity<?> CreateCampground(@RequestBody ModifyCampgroundRequest request) {
        String name = request.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Campground name cannot be empty");
        }

        campgroundService.createCampground(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateCampground(@PathVariable String id, @RequestBody ModifyCampgroundRequest request) {
        String name = request.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Campground name cannot be empty");
        }

        campgroundService.updateCampground(id, name);
        return ResponseEntity.ok().build();
    }
}
