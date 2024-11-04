package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator validator;

    @Autowired
    public UserService(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        ValidationUtils.validateEntity(user, validator);
        userRepository.save(user);
    }

    public void updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setRole(user.getRole());
        existingUser.setPasswordHash(user.getPasswordHash());
        existingUser.setBranches(user.getBranches());

        User existingUserByEmail = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (existingUserByEmail != null && !existingUserByEmail.getId().equals(id)) {
            throw new DuplicateResourceException("Another user already has the same email");
        }

        ValidationUtils.validateEntity(existingUser, validator);
        userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
