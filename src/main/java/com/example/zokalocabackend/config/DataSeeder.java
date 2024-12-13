package com.example.zokalocabackend.config;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
@Profile("!test")
public class DataSeeder {
    private final PasswordEncodingService passwordEncodingService;

    @Value("${application.root-user.email}")
    private String rootUserEmail;

    @Value("${application.root-user.first-name}")
    private String rootUserFirstName;

    @Value("${application.root-user.last-name}")
    private String rootUserLastName;

    @Value("${application.root-user.password}")
    private String rootUserPassword;

    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail(rootUserEmail);
                user.setFirstName(rootUserFirstName);
                user.setLastName(rootUserLastName);
                user.setRole(UserRole.ADMIN);
                user.setPasswordHash(passwordEncodingService.encodePassword(rootUserPassword));
                userRepository.save(user);
            }
        };
    }
}