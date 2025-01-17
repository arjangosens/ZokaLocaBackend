package com.example.zokalocabackend.features.chat.presentation.responses;

import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.BranchDTO;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.MessageDTO;

import java.util.List;

public record GetConversationByIdResponse (
        String id,
        String name,
        List<BranchDTO> branches
) {
}
