package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.LoginRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.AuthResponse;
import com.example.zokalocabackend.features.usermanagement.services.AuthenticationService;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
import com.example.zokalocabackend.features.usermanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    private final UserService userService;
    private final PasswordEncodingService passwordEncodingService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = new AuthResponse(authService.authenticate(request.email(), request.password()));
        return ResponseEntity.ok(response);
    }
}
