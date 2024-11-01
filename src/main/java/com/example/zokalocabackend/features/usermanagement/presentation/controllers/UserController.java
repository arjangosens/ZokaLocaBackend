package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.UserMapper;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.RegisterUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserResponse;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final BranchService branchService;
    private final PasswordEncodingService passwordEncodingService;

    @Autowired
    public UserController(UserService userService, BranchService branchService, PasswordEncodingService passwordEncodingService) {
        this.userService = userService;
        this.branchService = branchService;
        this.passwordEncodingService = passwordEncodingService;
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.toGetUserResponsesList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable @NotBlank String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toGetUserResponse(user));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        String passwordHash = passwordEncodingService.encodePassword(registerUserRequest.password());
        Set<Branch> branches = extractBranches(registerUserRequest.branchIds());
        userService.createUser(UserMapper.toUser(registerUserRequest, passwordHash, branches));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable @NotBlank String id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User existingUser = userService.getUserById(id);
        Set<Branch> branches = extractBranches(updateUserRequest.branchIds());
        User updatedUser = UserMapper.toUser(updateUserRequest, existingUser, branches);
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @NotBlank String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    private Set<Branch> extractBranches(List<String> branchIds) {
        Set<Branch> branches = new HashSet<>();

        if (branchIds != null) {
            for (String branchId : branchIds) {
                Branch branch = branchService.getBranchById(branchId);
                branches.add(branch);
            }
        }

        return branches;
    }
}
