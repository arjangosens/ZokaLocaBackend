package com.example.zokalocabackend.features.usermanagement.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Branch {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;

    @NotBlank
    private String name;

    public Branch(String name) {
        this.name = name;
    }
}