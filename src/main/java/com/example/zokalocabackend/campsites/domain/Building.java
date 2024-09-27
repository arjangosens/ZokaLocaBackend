package com.example.zokalocabackend.campsites.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@TypeAlias("building")
public class Building extends Campsite {
    private int numOfRooms;
    private int numOfCommonAreas;

    public Building() {
        super();
    }
}
