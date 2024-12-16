package com.example.zokalocabackend.features.chat.presentation.mappers;

import com.example.zokalocabackend.features.chat.domain.Message;
import com.example.zokalocabackend.features.chat.presentation.requests.SendMessageRequest;

public class MessageMapper {
    public static Message toDomain(SendMessageRequest request, String conversationId, String authorId) {
        return new Message(
                null,
                request.content(),
                conversationId,
                authorId
        );
    }
}
