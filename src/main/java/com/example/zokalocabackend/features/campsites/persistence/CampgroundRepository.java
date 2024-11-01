package com.example.zokalocabackend.features.campsites.persistence;

import com.example.zokalocabackend.features.campsites.domain.Campground;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampgroundRepository extends MongoRepository<Campground, String> {
    boolean existsByNameIgnoreCase(String name);
    Campground findByNameIgnoreCase(String name);
}
