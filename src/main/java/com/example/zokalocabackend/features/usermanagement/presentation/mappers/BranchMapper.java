package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.BranchCollectionItemDTO;

import java.util.ArrayList;
import java.util.List;

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
}
