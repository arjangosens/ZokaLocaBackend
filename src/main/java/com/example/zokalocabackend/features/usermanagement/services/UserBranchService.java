package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing the relationship between users and branches.
 */
@Service
public class UserBranchService {
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserBranchService(BranchRepository branchRepository, UserRepository userRepository) {
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
    }

    /**
     * Sets the users for a given branch.
     * Also updates all the users associated with the branch.
     *
     * @param branchId the ID of the branch
     * @param userIds the IDs of the users to be associated with the branch
     * @throws NoSuchElementException if no branch with the specified ID is found
     */
    public void setBranchUsers(String branchId, String[] userIds) {
        Branch branch = branchRepository.findById(branchId).orElseThrow();
        branch.getUsers().clear();

        for (String userId : userIds) {
            User user = userRepository.findById(userId).orElseThrow();
            branch.getUsers().add(user);
            user.getBranches().add(branch);
            userRepository.save(user);
        }

        branchRepository.save(branch);
    }

    /**
     * Sets the branches for a given user.
     * Also updates all the branches to which the user is associated.
     *
     * @param userId the ID of the user
     * @param branchIds the IDs of the branches to be associated with the user
     * @throws NoSuchElementException if no user with the specified ID is found
     */
    public void setUserBranches(String userId, String[] branchIds) {
        User user = userRepository.findById(userId).orElseThrow();
        user.getBranches().clear();

        for (String branchId : branchIds) {
            Branch branch = branchRepository.findById(branchId).orElseThrow();
            user.getBranches().add(branch);
            branch.getUsers().add(user);
            branchRepository.save(branch);
        }

        userRepository.save(user);
    }

    /**
     * Removes a user from all branches.
     *
     * @param userId the ID of the user to be removed from all branches
     * @param excludedBranchIds the IDs of the branches from which the user should not be removed
     * @throws NoSuchElementException if no user with the specified ID is found
     */
    public void removeUserFromAllBranches(String userId, List<String> excludedBranchIds) {
        User user = userRepository.findById(userId).orElseThrow();

        // Create empty list if excludedBranchIds is null
        if (excludedBranchIds == null) {
            excludedBranchIds = new ArrayList<>();
        }

        for (Branch branch : user.getBranches()) {
            if (!excludedBranchIds.contains(branch.getId())) {
                branch.getUsers().remove(user);
                branchRepository.save(branch);
            }
        }

        user.getBranches().clear();
        userRepository.save(user);
    }
}