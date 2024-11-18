package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserBranch;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
import com.example.zokalocabackend.features.usermanagement.persistence.UserBranchRepository;
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
    private final UserBranchRepository userBranchRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public UserBranchService(UserBranchRepository userBranchRepository, UserRepository userRepository, BranchRepository branchRepository) {
        this.userBranchRepository = userBranchRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
    }

    /**
     * Checks if a UserBranch relationship exists by user ID and branch ID.
     *
     * @param userId the ID of the user
     * @param branchId the ID of the branch
     * @return true if the relationship exists, false otherwise
     */
    public boolean existsUserBranchByUserIdAndBranchId(String userId, String branchId) {
        return userBranchRepository.existsByUserIdAndBranchId(userId, branchId);
    }

    /**
     * Retrieves all branches associated with a given user ID.
     *
     * @param userId the ID of the user whose branches are to be retrieved
     * @return a list of branches associated with the specified user
     */
    public List<Branch> getAllBranchesByUserId(String userId) {
        List<UserBranch> userBranches = userBranchRepository.findAllByUserId(userId);
        List<Branch> branches = new ArrayList<>();

        for (UserBranch userBranch : userBranches) {
            branches.add(userBranch.getBranch());
        }

        return branches;
    }

    /**
     * Retrieves all users associated with a given branch ID.
     *
     * @param branchId the ID of the branch whose users are to be retrieved
     * @return a list of users associated with the specified branch
     */
    public List<User> getAllUsersByBranchId(String branchId) {
        List<UserBranch> userBranches = userBranchRepository.findAllByBranchId(branchId);
        List<User> users = new ArrayList<>();

        for (UserBranch userBranch : userBranches) {
            users.add(userBranch.getUser());
        }

        return users;
    }

    /**
     * Sets the users for a given branch.
     * Also updates all the users associated with the branch.
     *
     * @param branch the branch that the users will be associated with
     * @param userIds the IDs of the users to be associated with the branch
     * @throws NoSuchElementException if no user with the specified ID is found
     */
    public void setBranchUsers(Branch branch, List<String> userIds) {
        List<UserBranch> userBranches = userBranchRepository.findAllByBranchId(branch.getId());

        for (UserBranch userBranch : userBranches) {
            if (!userIds.contains(userBranch.getUser().getId())) {
                userBranchRepository.delete(userBranch);
            }
        }

        for (String userId : userIds) {
            User user = userRepository.findById(userId).orElseThrow();
            if (!userBranchRepository.existsByUserIdAndBranchId(userId, branch.getId())) {
                UserBranch userBranch = UserBranch.builder()
                        .user(user)
                        .branch(branch)
                        .build();
                userBranchRepository.save(userBranch);
            }
        }
    }

    /**
     * Sets the branches for a given user.
     * Also updates all the branches to which the user is associated.
     *
     * @param user the user that the branches will be associated with
     * @param branchIds the IDs of the branches to be associated with the user
     * @throws NoSuchElementException if no branch with the specified ID is found
     */
    public void setUserBranches(User user, List<String> branchIds) {
        List<UserBranch> userBranches = userBranchRepository.findAllByUserId(user.getId());

        for (UserBranch userBranch : userBranches) {
            if (!branchIds.contains(userBranch.getBranch().getId())) {
                userBranchRepository.delete(userBranch);
            }
        }

        for (String branchId : branchIds) {
            Branch branch = branchRepository.findById(branchId).orElseThrow();
            if (!userBranchRepository.existsByUserIdAndBranchId(user.getId(), branchId)) {
                UserBranch userBranch = UserBranch.builder()
                        .user(user)
                        .branch(branch)
                        .build();
                userBranchRepository.save(userBranch);
            }
        }
    }

    /**
     * Removes a user from all branches.
     *
     * @param user the user to be removed from all branches
     */
    public void removeUserFromAllBranches(User user) {
        userBranchRepository.deleteAllByUser(user);
    }
}