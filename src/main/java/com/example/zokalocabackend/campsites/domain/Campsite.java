package com.example.zokalocabackend.campsites.domain;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

public class Campsite {
    private String id;
    private String name;
    private CampsiteType campsiteType;
    private String description;
    private Address address;
    private PersonLimit personLimit;
    private CampsitePrice price;
    private CampGround campGround;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int numOfToilets;
    private int numOfShowers;
    private int numOfWaterSources;
    private HashMap<String, SurroundingProximity> surroundings;
    private Set<Facility> facilities;
}
