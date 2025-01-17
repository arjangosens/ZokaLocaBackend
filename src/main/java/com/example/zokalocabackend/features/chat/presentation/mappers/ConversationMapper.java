package com.example.zokalocabackend.features.chat.presentation.mappers;

import com.example.zokalocabackend.features.chat.domain.Conversation;
import com.example.zokalocabackend.features.chat.domain.Message;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.BranchDTO;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.ConversationItemDTO;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.MessageDTO;
import com.example.zokalocabackend.features.chat.presentation.requests.CreateConversationRequest;
import com.example.zokalocabackend.features.chat.presentation.responses.GetConversationByIdResponse;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ConversationMapper {
    public static Conversation toDomain(CreateConversationRequest request) {
        return new Conversation(
                null,
                request.name(),
                request.branchIds()
        );
    }

    public static ConversationItemDTO toConversationItemDTO(Conversation conversation, Message lastMessage, List<Branch> branches) {
        MessageDTO lastMessageDTO = null;

        if (lastMessage != null) {
            lastMessageDTO = MessageMapper.toMessageDTO(lastMessage);
        }

        return new ConversationItemDTO(
                conversation.getId(),
                conversation.getName(),
                lastMessageDTO,
                branches.stream()
                        .map(branch -> new BranchDTO(branch.getId(), branch.getName()))
                        .collect(Collectors.toList())
        );
    }

    public static GetConversationByIdResponse toGetConversationByIdResponse(Conversation conversation, List<Branch> branches) {
        return new GetConversationByIdResponse(
                conversation.getId(),
                conversation.getName(),
                branches.stream()
                        .map(branch -> new BranchDTO(branch.getId(), branch.getName()))
                        .collect(Collectors.toList())
        );
    }
}
