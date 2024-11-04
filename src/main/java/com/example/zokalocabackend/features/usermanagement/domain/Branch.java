package com.example.zokalocabackend.features.usermanagement.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;

    @NotBlank
    private String name;

    @DBRef(lazy = true)
    private Set<User> users = Set.of();

    public Branch(String name) {
        this.name = name;
    }

    public Branch(String id, String name) {
        this.id = id;
        this.name = name;
    }
}