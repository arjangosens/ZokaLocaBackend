package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.BranchFilter;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.CreateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.GetAllBranchesRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateBranchRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetAllBranchesListItemResponse;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetBranchByIdResponse;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
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

    public static GetBranchByIdResponse toGetBranchByIdResponse(Branch branch, List<User> users) {
        return new GetBranchByIdResponse(branch.getId(), branch.getName(), UserMapper.toUserCollectionItemDTOList(users));
    }

    public static GetAllBranchesListItemResponse toGetAllBranchesListItemResponse(Branch branch) {
        return new GetAllBranchesListItemResponse(branch.getId(), branch.getName());
    }

    public static Branch toBranch(CreateBranchRequest createBranchRequest) {
        return new Branch(null, createBranchRequest.name());
    }

    public static Branch toBranch(UpdateBranchRequest updateBranchRequest, String id) {
        return new Branch(id, updateBranchRequest.name());
    }

    public static BranchFilter toBranchFilter(GetAllBranchesRequest request) {
        return BranchFilter.builder()
                .name(request.name())
                .build();
    }
}
