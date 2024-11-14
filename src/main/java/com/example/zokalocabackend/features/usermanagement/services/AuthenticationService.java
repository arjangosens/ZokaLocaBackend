package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String authenticate(User user, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), password));
        return jwtService.generateToken(user);
    }
}
