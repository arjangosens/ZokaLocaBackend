package com.example.zokalocabackend.features.chat.presentation.datatransferobjects;

import java.time.Instant;

public record MessageDTO(
        String id,
        String content,
        UserDTO author,
        Instant timestamp
) {
}
