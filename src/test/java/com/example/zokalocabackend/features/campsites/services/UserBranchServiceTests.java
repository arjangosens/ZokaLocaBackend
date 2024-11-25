package com.example.zokalocabackend.features.campsites.services;

import com.example.zokalocabackend.BaseTest;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserBranch;
import com.example.zokalocabackend.features.usermanagement.domain.UserRole;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
import com.example.zokalocabackend.features.usermanagement.persistence.UserBranchRepository;
import com.example.zokalocabackend.features.usermanagement.persistence.UserRepository;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserBranchServiceTests extends BaseTest {

    @MockBean
    private UserBranchRepository userBranchRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BranchRepository branchRepository;

    @Autowired
    private UserBranchService userBranchService;

    @Test
    public void getAllBranchesByUserId_withValidId_returnsBranches() {
        // Arrange
        String userId = "67078988be2d215dbf64ea22";
        User user = new User(userId, "John", "Doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        Branch branch = new Branch("67078988be2d215dbf64ea23", "Arenden");
        Branch branch2 = new Branch("67078988be2d215dbf64ea24", "Beren");
        List<UserBranch> userBranches = new ArrayList<>();
        userBranches.add(new UserBranch("67078988be2d215dbf64ea19", user, branch));
        userBranches.add(new UserBranch("67078988be2d215dbf64ea20", user, branch2));
        given(this.userBranchRepository.findAllByUserId(userId)).willReturn(userBranches);

        // Act
        List<Branch> result = userBranchService.getAllBranchesByUserId(userId);

        // Assert
        assertThat(result).containsExactlyInAnyOrder(branch, branch2);
    }

    @Test
    public void getAllBranchesByUserId_withInvalidId_returnsEmptyList() {
        // Arrange
        String userId = "id";
        given(this.userBranchRepository.findAllByUserId(userId)).willReturn(new ArrayList<>());

        // Act
        List<Branch> result = userBranchService.getAllBranchesByUserId(userId);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void getAllUsersByBranchId_withValidId_returnsUsers() {
        // Arrange
        String branchId = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(branchId, "Arenden");
        User user = new User("67078988be2d215dbf64ea23", "john", "doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User user2 = new User("67078988be2d215dbf64ea24", "jane", "doe", "jane.doe@test.com", "hashedPassword", UserRole.ADMIN);
        List<UserBranch> userBranches = new ArrayList<>();
        userBranches.add(new UserBranch("67078988be2d215dbf64ea19", user, branch));
        userBranches.add(new UserBranch("67078988be2d215dbf64ea20", user2, branch));
        given(this.userBranchRepository.findAllByBranchId(branchId)).willReturn(userBranches);

        // Act
        List<User> result = userBranchService.getAllUsersByBranchId(branchId);

        // Assert
        assertThat(result).containsExactlyInAnyOrder(user, user2);
    }

    @Test
    public void getAllUsersByBranchId_withInvalidId_returnsEmptyList() {
        // Arrange
        String branchId = "id";
        given(this.userBranchRepository.findAllByBranchId(branchId)).willReturn(new ArrayList<>());

        // Act
        List<User> result = userBranchService.getAllUsersByBranchId(branchId);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void setBranchUsers_withValidBranchAndUserIds_updatesUsers() {
        // Arrange
        String userId = "67078988be2d215dbf64ea23";
        String userId2 = "67078988be2d215dbf64ea24";
        String userId3 = "67078988be2d215dbf64ea25";
        Branch branch = new Branch("67078988be2d215dbf64ea22", "Arenden");
        User user = new User(userId, "john", "doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        User user2 = new User(userId2, "jane", "doe", "jane.doe@test.com", "hashedPassword", UserRole.ADMIN);
        User user3 = new User(userId3, "jim", "doe", "jim.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        List<UserBranch> userBranches = new ArrayList<>();
        userBranches.add(new UserBranch("67078988be2d215dbf64ea19", user, branch));
        userBranches.add(new UserBranch("67078988be2d215dbf64ea20", user2, branch));
        given(this.userBranchRepository.findAllByBranchId(branch.getId())).willReturn(userBranches);
        given(this.userRepository.findById(userId)).willReturn(Optional.of(user));
        given(this.userRepository.findById(userId3)).willReturn(Optional.of(user3));
        given(this.userBranchRepository.existsByUserIdAndBranchId(userId, branch.getId())).willReturn(true);
        given(this.userBranchRepository.existsByUserIdAndBranchId(userId3, branch.getId())).willReturn(false);


        // Act
        userBranchService.setBranchUsers(branch, List.of(userId, userId3));

        // Assert
        verify(this.userBranchRepository).delete(argThat(userBranch -> userBranch.getUser().getId().equals(userId2)));
        verify(this.userBranchRepository).save(argThat(userBranch -> userBranch.getUser().getId().equals(userId3)));
    }

    @Test
    public void setBranchUsers_WithInvalidUserId_ThrowsException() {
        // Arrange
        String userId = "67078988be2d215dbf64ea23";
        Branch branch = new Branch("67078988be2d215dbf64ea22", "Arenden");

        given(this.userRepository.findById(userId)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userBranchService.setBranchUsers(branch, List.of(userId)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void setUserBranches_withValidUserAndBranchIds_updatesBranches() {
        // Arrange
        String branchId = "67078988be2d215dbf64ea22";
        String branchId2 = "67078988be2d215dbf64ea23";
        String branchId3 = "67078988be2d215dbf64ea24";
        User user = new User("67078988be2d215dbf64ea23", "john", "doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        Branch branch = new Branch(branchId, "Arenden");
        Branch branch2 = new Branch(branchId2, "Beren");
        Branch branch3 = new Branch(branchId3, "Haviken");

        List<UserBranch> userBranches = new ArrayList<>();
        userBranches.add(new UserBranch("67078988be2d215dbf64ea19", user, branch));
        userBranches.add(new UserBranch("67078988be2d215dbf64ea20", user, branch2));

        given(this.userBranchRepository.findAllByUserId(user.getId())).willReturn(userBranches);
        given(this.branchRepository.findById(branchId)).willReturn(Optional.of(branch));
        given(this.branchRepository.findById(branchId3)).willReturn(Optional.of(branch3));
        given(this.userBranchRepository.existsByUserIdAndBranchId(user.getId(), branchId)).willReturn(true);
        given(this.userBranchRepository.existsByUserIdAndBranchId(user.getId(), branchId3)).willReturn(false);

        // Act
        userBranchService.setUserBranches(user, List.of(branchId, branchId3));

        // Assert
        verify(this.userBranchRepository).delete(argThat(userBranch -> userBranch.getBranch().getId().equals(branchId2)));
        verify(this.userBranchRepository).save(argThat(userBranch -> userBranch.getBranch().getId().equals(branchId3)));
    }

    @Test
    public void setUserBranches_WithInvalidBranchId_ThrowsException() {
        // Arrange
        String branchId = "67078988be2d215dbf64ea22";
        User user = new User("67078988be2d215dbf64ea23", "john", "doe", "john.doe@test.com", "hashedPassword", UserRole.VOLUNTEER);
        given(this.branchRepository.findById(branchId)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userBranchService.setUserBranches(user, List.of(branchId)))
                .isInstanceOf(NoSuchElementException.class);
    }
}
