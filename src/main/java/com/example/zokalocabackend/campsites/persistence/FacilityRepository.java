package com.example.zokalocabackend.campsites.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.zokalocabackend.campsites.domain.Facility;

public interface FacilityRepository extends MongoRepository<Facility, String> {
    boolean existsByNameIgnoreCase(String name);
    Facility findByNameIgnoreCase(String name);
}
