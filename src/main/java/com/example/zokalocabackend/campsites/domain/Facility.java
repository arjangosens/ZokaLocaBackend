package com.example.zokalocabackend.campsites.domain;

import org.springframework.data.annotation.Id;

public class Facility {
    @Id
    private String id;
    private String name;

    public Facility() {}

    public Facility(String name) {
        this.name = name;
    }

    public Facility(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
