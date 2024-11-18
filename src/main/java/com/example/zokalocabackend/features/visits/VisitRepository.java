package com.example.zokalocabackend.features.visits;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitRepository extends MongoRepository<Visit, String> {
    List<Visit> findAllByCampsite_Id(String campsiteId);
    List<Visit> findAllByBranch_Id(String branchId);
}
