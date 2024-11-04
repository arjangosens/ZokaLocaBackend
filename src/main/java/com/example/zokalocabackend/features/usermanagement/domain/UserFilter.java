package com.example.zokalocabackend.features.usermanagement.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String branchId;
}
