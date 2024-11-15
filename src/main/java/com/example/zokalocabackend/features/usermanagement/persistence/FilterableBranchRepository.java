package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableBranchRepository {
    Page<Branch> findAllWithFilters(Pageable pageable, BranchFilter filter);
}
