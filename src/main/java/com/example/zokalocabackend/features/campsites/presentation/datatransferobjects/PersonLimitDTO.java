package com.example.zokalocabackend.features.campsites.presentation.datatransferobjects;

import jakarta.validation.constraints.Min;

public record PersonLimitDTO(@Min(1) int minimum, @Min(0) int maximum) {
}
