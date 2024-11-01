package com.example.zokalocabackend.campsites.services;

import com.example.zokalocabackend.campsites.domain.Campground;
import com.example.zokalocabackend.campsites.persistence.CampgroundRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampgroundService {
    private final CampgroundRepository campgroundRepository;
    private final Validator validator;

    @Autowired
    public CampgroundService(CampgroundRepository campgroundRepository, Validator validator) {
        this.campgroundRepository = campgroundRepository;
        this.validator = validator;
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

        Campground campground = new Campground(name);
        ValidationUtils.validateEntity(campground, validator);
        campgroundRepository.save(campground);
    }

    public void updateCampground(String id, String name) {
        Campground campground = campgroundRepository.findById(id).orElseThrow();
        campground.setName(name);
        Campground existingCampground = campgroundRepository.findByNameIgnoreCase(name);
        
        if (existingCampground != null && !existingCampground.getId().equals(id)) {
            throw new DuplicateResourceException("Another campground already has the same name");
        }

        ValidationUtils.validateEntity(campground, validator);
        campgroundRepository.save(campground);
    }
}
