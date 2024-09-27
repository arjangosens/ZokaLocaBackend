package com.example.zokalocabackend.campsites.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the limit on the number of persons allowed.
 * If the maximum is set to 0, there is no maximum limit.
 */
@Getter
public class PersonLimit {
    private int minimum;

    @Setter
    private int maximum; // No maximum limit if set to 0

    public PersonLimit(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public void setMinimum(int minimum) {
        if (minimum < 0) {
            throw new IllegalArgumentException("Minimum cannot be negative");
        }
        this.minimum = minimum;
    }

}
