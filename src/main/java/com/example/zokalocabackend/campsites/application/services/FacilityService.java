package com.example.zokalocabackend.campsites.application.services;

import com.example.zokalocabackend.campsites.domain.Facility;
import com.example.zokalocabackend.campsites.persistence.FacilityRepository;
import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
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

        facilityRepository.save(new Facility(name));
    }

    public void updateFacility(String id, String name) {
        Facility facility = facilityRepository.findById(id).orElseThrow();
        facility.setName(name);
        facilityRepository.save(facility);
    }
}
