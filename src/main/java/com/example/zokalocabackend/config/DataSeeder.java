package com.example.zokalocabackend.config;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
@Profile("!test")
public class DataSeeder {
    private final PasswordEncodingService passwordEncodingService;

    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("a.gosens@student.fontys.nl");
                user.setFirstName("Arjan");
                user.setLastName("Gosens");
                user.setRole(UserRole.ADMIN);
                user.setPasswordHash(passwordEncodingService.encodePassword("Qwerty123!"));
                userRepository.save(user);
            }
        };
    }
}