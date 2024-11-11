package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserFilter;
import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTests {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void getUserById_withValidId_returnsUser() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        User user = new User(id, "John", "Doe", "j.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        given(this.userRepository.findById(id)).willReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(id);

        // Assert
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void getUserById_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.userRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getAllUsers_returnsUsers() {
        // Arrange
        User user1 = new User("67078988be2d215dbf64ea22", "John", "Doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User user2 = new User("67078988be2d215dbf64ea23", "Jane", "Doe", "jane.doe@test.com", "hashedPassword", UserRole.ADMIN);
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        UserFilter filter = new UserFilter();
        given(this.userRepository.findAllWithFilters(argThat(pageable::equals), argThat(filter::equals)))
                .willReturn(new PageImpl<>(List.of(user1, user2)));

        // Act
        Page<User> result = userService.getAllUsers(pageable, filter);

        // Assert
        assertThat(result.getContent()).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    public void createUser_withValidUser_returnsUser() {
        // Arrange
        User user = new User(null, "John", "Doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        given(this.userRepository.existsByEmailIgnoreCase(user.getEmail())).willReturn(false);

        // Act
        User result = userService.createUser(user);

        // Assert
        verify(this.userRepository).save(argThat(u -> u.equals(user)));
    }

    @Test
    public void createUser_withDuplicateEmail_throwsException() {
        // Arrange
        User user = new User(null, "John", "Doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        given(this.userRepository.existsByEmailIgnoreCase(user.getEmail())).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void createUser_withInvalidUser_throwsException() {
        // Arrange
        User user = new User(null, "", "", "not a valid email", "", null);

        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateUser_withValidUser_updatesUser() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        User user = new User(id, "John", "Doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User updatedUser = new User(id, "Jane", "Doe", "jane.doe@test.com", "hashedPassword", UserRole.ADMIN);
        given(this.userRepository.findById(id)).willReturn(Optional.of(user));
        given(this.userRepository.findByEmailIgnoreCase(updatedUser.getEmail())).willReturn(null);

        // Act
        userService.updateUser(id, updatedUser);

        // Assert
        verify(this.userRepository).save(argThat(u -> u.getId().equals(updatedUser.getId())));
    }

    @Test
    public void updateUser_withValidUserRetainingSameEmail_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        User user = new User(id, "John", "Doe", "j.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User existingUser = new User(id, "Jane", "Doe", "j.doe@test.com", "hashedPassword", UserRole.ADMIN);
        given(this.userRepository.findById(id)).willReturn(Optional.of(user));
        given(this.userRepository.findByEmailIgnoreCase(user.getEmail())).willReturn(existingUser);

        // Act
        userService.updateUser(id, user);

        // Assert
        verify(this.userRepository).save(argThat(u -> u.equals(user)));
    }

    @Test
    public void updateUser_withOtherUserHavingDuplicateName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        User user = new User(id, "John", "Doe", "j.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User existingUser = new User("67078988be2d215dbf64ea23", "Jane", "Doe", "j.doe@test.com", "hashedPassword", UserRole.ADMIN);
        given(this.userRepository.findById(id)).willReturn(Optional.of(user));
        given(this.userRepository.findByEmailIgnoreCase(user.getEmail())).willReturn(existingUser);

        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(id, user))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void updateUser_withInvalidUser_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        User user = new User(id, "", "", "not a valid email", "", null);
        given(this.userRepository.findById(id)).willReturn(Optional.of(new User()));

        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(id, user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void deleteUser_withValidId_deletesUser() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        given(this.userRepository.existsById(id)).willReturn(true);

        // Act
        userService.deleteUser(id);

        // Assert
        verify(this.userRepository).deleteById(id);
    }

    @Test
    public void deleteUser_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.userRepository.existsById(id)).willReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
