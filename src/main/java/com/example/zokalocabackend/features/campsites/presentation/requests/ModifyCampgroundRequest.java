package com.example.zokalocabackend.features.campsites.presentation.requests;

import jakarta.validation.constraints.NotBlank;

public record ModifyCampgroundRequest(@NotBlank String name) {

}
