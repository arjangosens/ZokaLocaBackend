package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.BranchMapper;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.CreateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetBranchResponse;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;
    private final UserService userService;

    @Autowired
    public BranchController(BranchService branchService, UserService userService) {
        this.branchService = branchService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<GetBranchResponse>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(BranchMapper.toGetBranchResponsesList(branches));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBranchResponse> getBranchById(@PathVariable @NotBlank String id) {
        Branch branch = branchService.getBranchById(id);
        return ResponseEntity.ok(BranchMapper.toGetBranchResponse(branch));
    }

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody @Valid CreateBranchRequest createBranchRequest) {
        Branch branch = BranchMapper.toBranch(createBranchRequest);
        branchService.createBranch(branch.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable @NotBlank String id, @RequestBody @Valid UpdateBranchRequest updateBranchRequest) {
        Set<User> users = extractUsers(updateBranchRequest.userIds());
        Branch branch = BranchMapper.toBranch(updateBranchRequest, id, users);
        branchService.updateBranch(id, branch);
        return ResponseEntity.ok().build();
    }

    private Set<User> extractUsers(List<String> userIds) {
        Set<User> users = new HashSet<>();

        if (userIds != null) {
            for (String userId : userIds) {
                User user = userService.getUserById(userId);
                users.add(user);
            }
        }

        return users;
    }
}
