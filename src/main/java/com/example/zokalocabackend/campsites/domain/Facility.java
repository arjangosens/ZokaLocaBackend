package com.example.zokalocabackend.campsites.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
public class Facility {
    @Id
    private String id;

    @Setter
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
