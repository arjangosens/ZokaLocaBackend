package com.example.zokalocabackend.features.campsites.persistence;

import com.example.zokalocabackend.features.campsites.domain.Facility;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacilityRepository extends MongoRepository<Facility, String> {
    boolean existsByNameIgnoreCase(String name);
    Facility findByNameIgnoreCase(String name);
}
