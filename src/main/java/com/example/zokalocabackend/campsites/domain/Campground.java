package com.example.zokalocabackend.campsites.domain;

public class Campground {
    private String id;
    private String name;

    public Campground() {}

    public Campground(String name) {
        this.name = name;
    }

    public Campground(String id, String name) {
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
