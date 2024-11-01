package com.example.zokalocabackend.features.usermanagement.presentation.mappers;

import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.RegisterUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.requests.UpdateUserRequest;
import com.example.zokalocabackend.features.usermanagement.presentation.responses.GetUserResponse;

import java.util.ArrayList;
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

    public static User toUser(RegisterUserRequest registerUserRequest, String passwordHash, Set<Branch> branches) {
        return new User(null, registerUserRequest.firstName(), registerUserRequest.lastName(), registerUserRequest.email(), passwordHash, registerUserRequest.role(), branches);
    }

    public static User toUser(UpdateUserRequest updateUserRequest, User existingUser, Set<Branch> branches) {
        return new User(existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail(), existingUser.getPasswordHash(), updateUserRequest.role(), branches);
    }
}
