package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserBranch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserBranchRepository extends MongoRepository<UserBranch, String> {
    List<UserBranch> findAllByUserId(String userId);
    List<UserBranch> findAllByBranchId(String branchId);
    UserBranch findByUserIdAndBranchId(String userId, String branchId);
    boolean existsByUserIdAndBranchId(String userId, String branchId);
    void deleteByUserIdAndBranchId(String userId, String branchId);
    void deleteAllByUser(User user);
    void deleteAllByBranch(Branch branch);
}
