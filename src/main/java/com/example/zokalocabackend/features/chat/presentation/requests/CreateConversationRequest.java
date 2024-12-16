package com.example.zokalocabackend.features.chat.presentation.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateConversationRequest(
        @NotBlank
        String name,

        @NotNull
        @Size(min = 1)
        Set<String> branchIds
) {
}
