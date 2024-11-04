package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final Validator validator;

    @Autowired
    public BranchService(BranchRepository branchRepository, Validator validator) {
        this.branchRepository = branchRepository;
        this.validator = validator;
    }

    public Branch getBranchById(String id) {
        return branchRepository.findById(id).orElseThrow();
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public void createBranch(String name) {
        if (branchRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Branch already exists");
        }

        Branch branch = new Branch(name);
        ValidationUtils.validateEntity(branch, validator);
        branchRepository.save(branch);
    }

    public void updateBranch(String id, Branch branch) {
        Branch existingBranch = branchRepository.findById(id).orElseThrow();
        existingBranch.setName(branch.getName());
        existingBranch.setUsers(branch.getUsers());
        Branch existingBranchByName = branchRepository.findByNameIgnoreCase(branch.getName());

        if (existingBranchByName != null && !existingBranchByName.getId().equals(id)) {
            throw new DuplicateResourceException("Another branch already has the same name");
        }

        ValidationUtils.validateEntity(existingBranch, validator);
        branchRepository.save(existingBranch);
    }
}
