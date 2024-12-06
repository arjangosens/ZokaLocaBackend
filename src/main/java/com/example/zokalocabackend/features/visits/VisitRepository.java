package com.example.zokalocabackend.features.visits;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitRepository extends MongoRepository<Visit, String> {
    List<Visit> findAllByCampsiteId(String campsiteId);
    List<Visit> findAllByBranchId(String branchId);
}
