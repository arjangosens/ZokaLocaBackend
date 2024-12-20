package com.example.zokalocabackend.features.chat.presentation.requests;

import jakarta.validation.constraints.NotBlank;

public record SendMessageRequest(
        @NotBlank
        String content
) {
}
