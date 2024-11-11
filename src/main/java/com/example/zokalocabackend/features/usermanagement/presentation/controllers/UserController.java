package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserFilter;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.UserMapper;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.GetAllUsersRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.RegisterUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetAllUsersListItemResponse;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserByIdResponse;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserBranchService userBranchService;
    private final PasswordEncodingService passwordEncodingService;

    @Autowired
    public UserController(UserService userService, UserBranchService userBranchService, PasswordEncodingService passwordEncodingService) {
        this.userService = userService;
        this.userBranchService = userBranchService;
        this.passwordEncodingService = passwordEncodingService;
    }

    @GetMapping
    public ResponseEntity<Page<GetAllUsersListItemResponse>> getAllUsers(GetAllUsersRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.sortOrder()), request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.pageSize(), sort);
        UserFilter filter = UserMapper.toUserFilter(request);

        Page<User> users = userService.getAllUsers(pageable, filter);
        Page<GetAllUsersListItemResponse> getUserResponses = users.map(UserMapper::toGetAllUsersListItemResponse);

        return ResponseEntity.ok(getUserResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable @NotBlank String id) {
        User user = userService.getUserById(id);
        List<Branch> branches = userBranchService.getAllBranchesByUserId(id);
        return ResponseEntity.ok(UserMapper.toGetUserByIdResponse(user, branches));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        String passwordHash = passwordEncodingService.encodePassword(registerUserRequest.password());
        User createdUser = userService.createUser(UserMapper.toUser(registerUserRequest, passwordHash));

        if (registerUserRequest.branchIds() != null && !registerUserRequest.branchIds().isEmpty()) {
            userBranchService.setUserBranches(createdUser, registerUserRequest.branchIds());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable @NotBlank String id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User existingUser = userService.getUserById(id);
        User updatedUser = UserMapper.toUser(updateUserRequest, existingUser);
        userService.updateUser(id, updatedUser);
        userBranchService.setUserBranches(updatedUser, updateUserRequest.branchIds());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @NotBlank String id) {
        User user = userService.getUserById(id);
        userBranchService.removeUserFromAllBranches(user);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
