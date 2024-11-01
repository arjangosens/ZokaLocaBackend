package com.example.zokalocabackend.features.usermanagement.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    private String id;

    @Setter
    @NotBlank
    private String name;

    @DBRef(lazy = true)
    private List<User> users;

    public Branch(String name) {
        this.name = name;
    }

    public Branch(String id, String name) {
        this.id = id;
        this.name = name;
    }
}