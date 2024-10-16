package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.Min;
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

    @Min(0)
    private int numOfRooms;

    @Min(0)
    private int numOfCommonAreas;

    public Building() {
        super();
    }
}
