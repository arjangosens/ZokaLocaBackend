package com.example.zokalocabackend.features.e2e;

import com.example.zokalocabackend.features.campsites.domain.Facility;
import com.example.zokalocabackend.features.campsites.persistence.FacilityRepository;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.services.PasswordEncodingService;
import com.example.zokalocabackend.features.usermanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Profile("e2e")
@RequestMapping("/e2e")
public class E2ETestController {
    private final MongoTemplate mongoTemplate;
    private final UserService userService;
    private final PasswordEncodingService passwordEncodingService;
    private final FacilityRepository facilityRepository;

    @Value("${application.root-user.email}")
    private String rootUserEmail;

    @Value("${application.root-user.first-name}")
    private String rootUserFirstName;

    @Value("${application.root-user.last-name}")
    private String rootUserLastName;

    @Value("${application.root-user.password}")
    private String rootUserPassword;

    @PostMapping("/reset")
    public ResponseEntity<?> resetData() {
        mongoTemplate.getDb().drop();
        User rootUser = new User(null, rootUserFirstName, rootUserLastName, rootUserEmail, passwordEncodingService.encodePassword(rootUserPassword), UserRole.ADMIN);
        userService.createUser(rootUser);

        // Insert facilities
        List<Facility> facilities = new ArrayList<>();
        facilities.add(new Facility("675ab1748e35e7a586e24bef", "Kampvuur"));
        facilities.add(new Facility("675ab1c5081840129f9d5e1f", "Keuken"));
        facilities.add(new Facility("675ab270ef574ecb4dc82431", "Elektriciteit"));
        facilities.add(new Facility("675ab1da99129cebb6a0cbac", "Keukengerei"));
        facilities.add(new Facility("675ab1e0d50b9d3136041729", "Pionierhout"));
        facilities.add(new Facility("675ab1e4b3b3b3b3b3b3b3b3", "Sprokkelen toegestaan"));

        facilityRepository.saveAll(facilities);

        return ResponseEntity.ok().build();
    }
}