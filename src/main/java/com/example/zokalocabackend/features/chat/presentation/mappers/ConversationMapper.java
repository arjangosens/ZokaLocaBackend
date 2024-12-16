package com.example.zokalocabackend.features.chat.presentation.mappers;

import com.example.zokalocabackend.features.chat.domain.Conversation;
import com.example.zokalocabackend.features.chat.presentation.requests.CreateConversationRequest;

public class ConversationMapper {
    public static Conversation toDomain(CreateConversationRequest request) {
        return new Conversation(
                null,
                request.name(),
                request.branchIds()
        );
    }
}
