package com.example.zokalocabackend.features.usermanagement.services;

import com.example.zokalocabackend.exceptions.DuplicateResourceException;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import com.example.zokalocabackend.features.usermanagement.persistence.BranchRepository;
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
public class BranchServiceTests {
    @MockBean
    private BranchRepository branchRepository;

    @Autowired
    private BranchService branchService;

    @Test
    public void getBranchById_withValidId_returnsBranch() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(id, "Arenden");
        given(this.branchRepository.findById(id)).willReturn(Optional.of(branch));

        // Act
        Branch result = branchService.getBranchById(id);

        // Assert
        assertThat(result).isEqualTo(branch);
    }

    @Test
    public void getBranchById_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        given(this.branchRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> branchService.getBranchById(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getAllBranches_returnsBranches() {
        // Arrange
        Branch branch1 = new Branch("67078988be2d215dbf64ea22", "Arenden");
        Branch branch2 = new Branch("67078988be2d215dbf64ea23", "Beren");
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        BranchFilter filter = new BranchFilter();
        given(this.branchRepository.findAllWithFilters(pageable, filter)).willReturn(new PageImpl<>(List.of(branch1, branch2)));

        // Act
        Page<Branch> result = branchService.getAllBranches(pageable, filter);

        // Assert
        assertThat(result.getContent()).containsExactly(branch1, branch2);
    }

    @Test
    public void createBranch_withValidName_createsBranch() {
        // Arrange
        String name = "Arenden";
        given(this.branchRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act
        branchService.createBranch(name);

        // Assert
        verify(this.branchRepository).save(argThat(branch -> branch.getName().equals(name)));
    }

    @Test
    public void createBranch_withDuplicateName_throwsException() {
        // Arrange
        String name = "Arenden";
        given(this.branchRepository.existsByNameIgnoreCase(name)).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> branchService.createBranch(name))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void createBranch_withInvalidName_throwsException() {
        // Arrange
        String name = "";
        given(this.branchRepository.existsByNameIgnoreCase(name)).willReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> branchService.createBranch(name))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateBranch_withValidBranch_updatesBranch() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(id, "Arenden");
        Branch existingBranch = new Branch(id, "Beren");
        given(this.branchRepository.findById(id)).willReturn(Optional.of(existingBranch));
        given(this.branchRepository.findByNameIgnoreCase(branch.getName())).willReturn(null);

        // Act
        branchService.updateBranch(id, branch);

        // Assert
        verify(this.branchRepository).save(argThat(b -> b.getName().equals(branch.getName())));
    }

    @Test
    public void updateBranch_withValidBranchRetainingSameName_updatesBranch() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(id, "Arenden");
        Branch existingBranch = new Branch(id, "Arenden");
        given(this.branchRepository.findById(id)).willReturn(Optional.of(existingBranch));
        given(this.branchRepository.findByNameIgnoreCase(branch.getName())).willReturn(existingBranch);

        // Act
        branchService.updateBranch(id, branch);

        // Assert
        verify(this.branchRepository).save(argThat(b -> b.getName().equals(branch.getName())));
    }

    @Test
    public void updateBranch_withOtherBranchHavingDuplicateName_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(id, "Arenden");
        Branch existingBranch = new Branch(id, "Beren");
        Branch otherBranchWithDuplicateName = new Branch("67078988be2d215dbf64ea23", "Arenden");
        given(this.branchRepository.findById(id)).willReturn(Optional.of(existingBranch));
        given(this.branchRepository.findByNameIgnoreCase(branch.getName())).willReturn(otherBranchWithDuplicateName);

        // Act & Assert
        assertThatThrownBy(() -> branchService.updateBranch(id, branch))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    public void updateBranch_withInvalidBranch_throwsException() {
        // Arrange
        String id = "67078988be2d215dbf64ea22";
        Branch branch = new Branch(id, "");
        Branch existingBranch = new Branch(id, "Beren");
        given(this.branchRepository.findById(id)).willReturn(Optional.of(existingBranch));

        // Act & Assert
        assertThatThrownBy(() -> branchService.updateBranch(id, branch))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateBranch_withInvalidId_throwsException() {
        // Arrange
        String id = "id";
        Branch branch = new Branch(id, "Arenden");
        given(this.branchRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> branchService.updateBranch(id, branch))
                .isInstanceOf(NoSuchElementException.class);
    }
}
