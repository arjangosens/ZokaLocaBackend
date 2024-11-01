package com.example.zokalocabackend.campsites.services;

import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.persistence.FacilityRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final Validator validator;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, Validator validator) {
        this.facilityRepository = facilityRepository;
        this.validator = validator;
    }

    public Facility getFacilityById(String id) {
        return facilityRepository.findById(id).orElseThrow();
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    public void createFacility(String name) {
        if (facilityRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Facility already exists");
        }

        Facility facility = new Facility(name);
        ValidationUtils.validateEntity(facility, validator);

        facilityRepository.save(facility);
    }

    public void updateFacility(String id, String name) {
        Facility facility = facilityRepository.findById(id).orElseThrow();
        facility.setName(name);

        Facility existingFacility = facilityRepository.findByNameIgnoreCase(name);
        if (existingFacility != null && !existingFacility.getId().equals(id)) {
            throw new DuplicateResourceException("Another facility already has the same name");
        }

        ValidationUtils.validateEntity(facility, validator);
        facilityRepository.save(facility);
    }
}