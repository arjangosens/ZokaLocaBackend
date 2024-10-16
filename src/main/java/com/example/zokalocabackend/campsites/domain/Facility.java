package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
public class Facility {
    @Id
    private String id;

    @Setter
    @NotBlank
    private String name;

    public Facility() {}

    public Facility(String name) {
        this.name = name;
    }

    public Facility(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
