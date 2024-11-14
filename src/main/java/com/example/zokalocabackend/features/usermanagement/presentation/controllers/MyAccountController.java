package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.UserMapper;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserByIdResponse;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/my-account")
public class MyAccountController {
    private final UserBranchService userBranchService;

    @GetMapping
    public ResponseEntity<GetUserByIdResponse> getMyAccountInfo(@AuthenticationPrincipal UserDetails userDetails) {
        User loggedInUser = (User) userDetails;
        List<Branch> branches = userBranchService.getAllBranchesByUserId(loggedInUser.getId());
        return ResponseEntity.ok(UserMapper.toGetUserByIdResponse(loggedInUser, branches));
    }
}
