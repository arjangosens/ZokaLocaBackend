package com.example.zokalocabackend.features.campsites.presentation.datatransferobjects;

import jakarta.validation.constraints.NotBlank;

public record FacilityDTO(String id, @NotBlank String name) { }
