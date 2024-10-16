package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@Document(collection = "campsite")
@TypeAlias("campsite")
public abstract class Campsite {

    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;

    @NonNull
    @NotBlank
    private String name;

    private String description;
    private Address address;
    private PersonLimit personLimit;
    private CampsitePrice price;
    private String arrivalTime;
    private String departureTime;

    @Min(0)
    private int numOfToilets;

    @Min(0)
    private int numOfShowers;

    @Min(0)
    private int numOfWaterSources;

    private HashMap<String, SurroundingProximity> surroundings;
    private HashMap<String, String> externalSources;

    @DBRef
    private Set<Facility> facilities;

    @DBRef
    private String campGroundId;

    public Campsite() {
    }
}
