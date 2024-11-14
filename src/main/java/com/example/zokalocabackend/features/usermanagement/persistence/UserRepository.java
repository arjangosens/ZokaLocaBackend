package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, FilterableUserRepository {
    boolean existsByEmailIgnoreCase(String email);
    User findByEmailIgnoreCase(String email);
    Optional<User> findByEmail(String email);
}
