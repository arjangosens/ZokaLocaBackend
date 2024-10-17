package com.example.zokalocabackend.campsites.presentation.requests;

import jakarta.validation.constraints.NotBlank;

public record ModifyFacilityRequest(@NotBlank String name) { }