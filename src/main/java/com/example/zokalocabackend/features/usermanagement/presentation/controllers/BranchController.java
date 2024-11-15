package com.example.zokalocabackend.features.usermanagement.presentation.controllers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.mappers.BranchMapper;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.CreateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.GetAllBranchesRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetAllBranchesListItemResponse;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetBranchByIdResponse;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;
    private final UserBranchService userBranchService;

    @Autowired
    public BranchController(BranchService branchService, UserBranchService userBranchService) {
        this.branchService = branchService;
        this.userBranchService = userBranchService;
    }

    @GetMapping
    public ResponseEntity<Page<GetAllBranchesListItemResponse>> getAllBranches(GetAllBranchesRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.sortOrder()), request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.pageSize(), sort);
        BranchFilter filter = BranchMapper.toBranchFilter(request);

        Page<Branch> branches = branchService.getAllBranches(pageable, filter);
        Page<GetAllBranchesListItemResponse> getBranchResponses = branches.map(BranchMapper::toGetAllBranchesListItemResponse);

        return ResponseEntity.ok(getBranchResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBranchByIdResponse> getBranchById(@PathVariable @NotBlank String id) {
        Branch branch = branchService.getBranchById(id);
        List<User> users = userBranchService.getAllUsersByBranchId(branch.getId());
        return ResponseEntity.ok(BranchMapper.toGetBranchByIdResponse(branch, users));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody @Valid CreateBranchRequest createBranchRequest) {
        Branch branch = BranchMapper.toBranch(createBranchRequest);
        branchService.createBranch(branch.getName());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable @NotBlank String id, @RequestBody @Valid UpdateBranchRequest updateBranchRequest) {
        Branch branch = BranchMapper.toBranch(updateBranchRequest, id);
        branchService.updateBranch(id, branch);
        userBranchService.setBranchUsers(branch, updateBranchRequest.userIds());

        return ResponseEntity.ok().build();
    }
}
