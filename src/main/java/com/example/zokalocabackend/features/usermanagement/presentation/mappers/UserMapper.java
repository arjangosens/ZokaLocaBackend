package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.datatransferobjects.UserCollectionItemDTO;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.RegisterUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapper {
    public static GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), BranchMapper.toBranchCollectionItemDTOList(user.getBranches().stream().toList()));
    }

    public static List<GetUserResponse> toGetUserResponsesList(List<User> users) {
        List<GetUserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            userResponses.add(toGetUserResponse(user));
        }

        return userResponses;
    }

    public static User toUser(RegisterUserRequest registerUserRequest, String passwordHash) {
        return new User(null, registerUserRequest.firstName(), registerUserRequest.lastName(), registerUserRequest.email(), passwordHash, registerUserRequest.role(), new HashSet<>());
    }

    public static User toUser(UpdateUserRequest updateUserRequest, User existingUser, Set<Branch> branches) {
        return new User(existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail(), existingUser.getPasswordHash(), updateUserRequest.role(), branches);
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
}
