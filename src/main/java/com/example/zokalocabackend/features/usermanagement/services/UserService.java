package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserFilter;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import com.example.zokalocabackend.utilities.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator validator;

    @Autowired
    public UserService(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws NoSuchElementException if no user with the specified ID is found
     */
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    /**
     * Retrieves all users.
     *
     * @param pageable the pagination information
     * @param filter the filter to apply
     * @return a list of all users
     */
    public Page<User> getAllUsers(Pageable pageable, UserFilter filter) {
        return userRepository.findAllWithFilters(pageable, filter);
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     * @throws DuplicateResourceException if a user with the same email already exists
     * @throws ConstraintViolationException if the user entity is invalid
     */
    public User createUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        ValidationUtils.validateEntity(user, validator);
        return userRepository.save(user);
    }

    /**
     * Updates a user.
     * <p>
     * Note: This method does not update the branches of the user. The UserBranchService should be used for that.
     *
     * @param id the ID of the user to update
     * @param user the updated user
     * @throws NoSuchElementException if no user with the specified ID is found
     * @throws DuplicateResourceException if another user already has the same email
     * @throws ConstraintViolationException if the updated user entity is invalid
     */
    public void updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setRole(user.getRole());
        existingUser.setPasswordHash(user.getPasswordHash());

        User existingUserByEmail = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (existingUserByEmail != null && !existingUserByEmail.getId().equals(id)) {
            throw new DuplicateResourceException("Another user already has the same email");
        }

        ValidationUtils.validateEntity(existingUser, validator);
        userRepository.save(existingUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws NoSuchElementException if no user with the specified ID is found
     */
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
