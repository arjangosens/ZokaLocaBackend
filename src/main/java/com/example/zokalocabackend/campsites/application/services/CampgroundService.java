package com.example.zokalocabackend.campsites.application.services;

import com.example.zokalocabackend.campsites.domain.Campground;
import com.example.zokalocabackend.campsites.persistence.CampgroundRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampgroundService {
    private final CampgroundRepository campgroundRepository;

    @Autowired
    public CampgroundService(CampgroundRepository campgroundRepository) {
        this.campgroundRepository = campgroundRepository;
    }

    public Campground getCampgroundById(String id) {
        return campgroundRepository.findById(id).orElseThrow();
    }

    public List<Campground> getAllCampgrounds() {
        return campgroundRepository.findAll();
    }

    public void createCampground(String name) {
        if (campgroundRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Campground already exists");
        }

        campgroundRepository.save(new Campground(name));
    }

    public void updateCampground(String id, String name) {
        Campground campground = campgroundRepository.findById(id).orElseThrow();
        campground.setName(name);
        campgroundRepository.save(campground);
    }
}
