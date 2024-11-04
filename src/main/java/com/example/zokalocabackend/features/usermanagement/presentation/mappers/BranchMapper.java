package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.CreateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.GetAllBranchesRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetBranchResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BranchMapper {
    public static BranchCollectionItemDTO toBranchCollectionItemDTO(Branch branch) {
        return new BranchCollectionItemDTO(branch.getId(), branch.getName());
    }

    public static List<BranchCollectionItemDTO> toBranchCollectionItemDTOList(List<Branch> branches) {
        List<BranchCollectionItemDTO> branchCollectionItemDTOs = new ArrayList<>();

        for (Branch branch : branches) {
            branchCollectionItemDTOs.add(toBranchCollectionItemDTO(branch));
        }

        return branchCollectionItemDTOs;
    }

    public static GetBranchResponse toGetBranchResponse(Branch branch) {
        return new GetBranchResponse(branch.getId(), branch.getName(), UserMapper.toUserCollectionItemDTOList(branch.getUsers().stream().toList()));
    }

    public static List<GetBranchResponse> toGetBranchResponsesList(List<Branch> branches) {
        List<GetBranchResponse> branchResponses = new ArrayList<>();

        for (Branch branch : branches) {
            branchResponses.add(toGetBranchResponse(branch));
        }

        return branchResponses;
    }

    public static Branch toBranch(CreateBranchRequest createBranchRequest) {
        return new Branch(null, createBranchRequest.name(), new HashSet<>());
    }

    public static Branch toBranch(UpdateBranchRequest updateBranchRequest, String id, Set<User> users) {
        return new Branch(id, updateBranchRequest.name(), users);
    }

    public static BranchFilter toBranchFilter(GetAllBranchesRequest request) {
        return BranchFilter.builder()
                .name(request.name())
                .build();
    }
}
