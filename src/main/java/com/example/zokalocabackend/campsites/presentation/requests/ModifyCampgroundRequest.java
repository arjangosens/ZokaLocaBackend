package com.example.zokalocabackend.campsites.presentation.requests;

import jakarta.validation.constraints.NotBlank;

public record ModifyCampgroundRequest(@NotBlank String name) {

}
