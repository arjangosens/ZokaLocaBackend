package com.example.zokalocabackend.campsites.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.zokalocabackend.campsites.domain.Facility;

public interface FacilityRepository extends MongoRepository<Facility, String> {
    boolean existsByName(String name);
}
