package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepository extends MongoRepository<Branch, String>, FilterableBranchRepository {
    boolean existsByNameIgnoreCase(String name);
    Branch findByNameIgnoreCase(String name);
}
