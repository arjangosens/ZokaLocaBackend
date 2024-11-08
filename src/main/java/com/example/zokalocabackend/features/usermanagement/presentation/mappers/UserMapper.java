package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.domain.UserFilter;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.UserCollectionItemDTO;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.GetAllUsersRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.RegisterUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetAllUsersListItemResponse;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserByIdResponse;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static GetUserByIdResponse toGetUserByIdResponse(User user, List<Branch> branches) {
        return new GetUserByIdResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), BranchMapper.toBranchCollectionItemDTOList(branches));
    }

    public static GetAllUsersListItemResponse toGetAllUsersListItemResponse(User user) {
        return new GetAllUsersListItemResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }

    public static User toUser(RegisterUserRequest registerUserRequest, String passwordHash) {
        return new User(null, registerUserRequest.firstName(), registerUserRequest.lastName(), registerUserRequest.email(), passwordHash, registerUserRequest.role());
    }

    public static User toUser(UpdateUserRequest updateUserRequest, User existingUser) {
        return new User(existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail(), existingUser.getPasswordHash(), updateUserRequest.role());
    }

    public static UserCollectionItemDTO toUserCollectionItemDTO(User user) {
        return new UserCollectionItemDTO(user.getId(), user.getFirstName(), user.getLastName());
    }

    public static List<UserCollectionItemDTO> toUserCollectionItemDTOList(List<User> users) {
        List<UserCollectionItemDTO> userCollectionItemDTOs = new ArrayList<>();

        for (User user : users) {
            userCollectionItemDTOs.add(toUserCollectionItemDTO(user));
        }

        return userCollectionItemDTOs;
    }

    public static UserFilter toUserFilter(GetAllUsersRequest request) {
        return UserFilter.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .role(request.role())
                .branchId(request.branchId())
                .build();
    }
}
