package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Setter
@Getter
@SuperBuilder
@TypeAlias("field")
public class Field extends Campsite {

    @Min(0)
    private int sizeSquareMeters;

    public Field() {
        super();
    }
}
