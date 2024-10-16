package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
public class Campground {
    @Id
    private String id;

    @Setter
    @NotBlank
    private String name;

    public Campground() {}

    public Campground(String name) {
        this.name = name;
    }

    public Campground(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
