package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing branches.
 */
@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final Validator validator;

    @Autowired
    public BranchService(BranchRepository branchRepository, Validator validator) {
        this.branchRepository = branchRepository;
        this.validator = validator;
    }

    /**
     * Checks if a branch with the specified ID exists.
     *
     * @param id the ID of the branch to check
     * @return true if a branch with the specified ID exists, false otherwise
     */
    public boolean existsBranchById(String id) {
        return branchRepository.existsById(id);
    }

    /**
     * Retrieves a branch by its ID.
     *
     * @param id the ID of the branch to retrieve
     * @return the branch with the specified ID
     * @throws NoSuchElementException if no branch with the specified ID is found
     */
    public Branch getBranchById(String id) {
        return branchRepository.findById(id).orElseThrow();
    }

    /**
     * Retrieves all branches.
     *
     * @param pageable the pagination information
     * @param filter the filter to apply
     * @return a list of all branches
     */
    public Page<Branch> getAllBranches(Pageable pageable, BranchFilter filter) {
        return branchRepository.findAllWithFilters(pageable, filter);
    }

    /**
     * Creates a new branch.
     *
     * @param name the name of the branch to create
     * @throws DuplicateResourceException if a branch with the same name already exists
     * @throws ConstraintViolationException if the branch entity is invalid
     */
    public void createBranch(String name) {
        if (branchRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Branch already exists");
        }

        Branch branch = new Branch(name);
        ValidationUtils.validateEntity(branch, validator);
        branchRepository.save(branch);
    }

    /**
     * Updates a branch.
     *
     * @param id the ID of the branch to update
     * @param branch the branch to update
     * @throws DuplicateResourceException if another branch already has the same name
     * @throws ConstraintViolationException if the branch entity is invalid
     */
    public void updateBranch(String id, Branch branch) {
        Branch existingBranch = branchRepository.findById(id).orElseThrow();
        existingBranch.setName(branch.getName());
        Branch existingBranchByName = branchRepository.findByNameIgnoreCase(branch.getName());

        if (existingBranchByName != null && !existingBranchByName.getId().equals(id)) {
            throw new DuplicateResourceException("Another branch already has the same name");
        }

        ValidationUtils.validateEntity(existingBranch, validator);
        branchRepository.save(existingBranch);
    }
}
