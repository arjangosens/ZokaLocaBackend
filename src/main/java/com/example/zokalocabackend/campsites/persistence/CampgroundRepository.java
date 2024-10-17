package com.example.zokalocabackend.campsites.persistence;

import com.example.zokalocabackend.campsites.domain.Campground;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampgroundRepository extends MongoRepository<Campground, String> {
    boolean existsByNameIgnoreCase(String name);
    Campground findByNameIgnoreCase(String name);
}
