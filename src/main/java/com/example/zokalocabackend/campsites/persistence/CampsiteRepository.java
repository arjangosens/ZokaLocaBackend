package com.example.zokalocabackend.campsites.persistence;

import com.example.zokalocabackend.campsites.domain.Campsite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampsiteRepository extends MongoRepository<Campsite, String>, FilterableCampsiteRepository {
    boolean existsByNameIgnoreCase(String name);
    Campsite findByNameIgnoreCase(String name);
}
