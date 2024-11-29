package com.example.zokalocabackend.features.campsites.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.HashSet;
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

    @Valid
    private Address address;

    @Valid
    private PersonLimit personLimit;

    @Valid
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

    private String thumbnailAssetId;

    @Builder.Default
    private Set<String> imageIds = new HashSet<>();

    public Campsite() {
        this.imageIds = new HashSet<>();
    }
}
