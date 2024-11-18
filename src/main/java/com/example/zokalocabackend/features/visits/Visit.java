package com.example.zokalocabackend.features.visits;

import com.example.zokalocabackend.features.campsites.domain.Campsite;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Visit {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @NotNull
    private LocalDate arrivalDate;

    @NotNull
    private LocalDate departureDate;

    @NotNull
    @Min(1)
    @Max(10)
    private int rating;

    @NotNull
    @DBRef(lazy = true)
    private Branch branch;

    @NotNull
    @DBRef(lazy = true)
    private Campsite campsite;

    @Min(1)
    private Integer numOfPeople;

    @Min(0)
    private Double price;

    private String description;

    @Builder.Default
    private Set<String> pros = new HashSet<>();

    @Builder.Default
    private Set<String> cons = new HashSet<>();

    
}
