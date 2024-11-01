package com.example.zokalocabackend.features.campsites.domain;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the limit on the number of persons allowed.
 * If the maximum is set to 0, there is no maximum limit.
 */
@Getter
@Setter
public class PersonLimit {

    @Min(1)
    private int minimum;

    @Min(0)
    private int maximum; // No maximum limit if set to 0

    public PersonLimit(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }
}
