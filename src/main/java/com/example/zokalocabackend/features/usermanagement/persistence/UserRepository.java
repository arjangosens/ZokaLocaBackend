package com.example.zokalocabackend.features.usermanagement.persistence;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, FilterableUserRepository {
    boolean existsByEmailIgnoreCase(String email);
    User findByEmailIgnoreCase(String email);
}
