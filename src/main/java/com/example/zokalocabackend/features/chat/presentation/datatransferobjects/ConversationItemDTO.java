package com.example.zokalocabackend.features.chat.presentation.datatransferobjects;

import java.util.List;

public record ConversationItemDTO(
        String id,
        String name,
        MessageDTO lastMessage,
        List<BranchDTO> branches
) {
}
