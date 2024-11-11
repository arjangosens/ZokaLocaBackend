package com.example.zokalocabackend.features.usermanagement.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
@CompoundIndex(name = "user_branch_idx", def = "{'user': 1, 'branch': 1}", unique = true)
public class UserBranch {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Branch branch;
}
