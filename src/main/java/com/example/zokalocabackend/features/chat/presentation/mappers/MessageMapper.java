package com.example.zokalocabackend.features.chat.presentation.mappers;

import com.example.zokalocabackend.features.chat.domain.Message;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.UserDTO;
import com.example.zokalocabackend.features.chat.presentation.requests.SendMessageRequest;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.MessageDTO;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageMapper {
    public static Message toDomain(SendMessageRequest request, String conversationId, User author) {
        return new Message(
                null,
                request.content(),
                conversationId,
                author
        );
    }

    public static MessageDTO toMessageDTO(Message message) {
        User author = message.getAuthor();
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                new UserDTO(author.getId(), author.getFirstName(), author.getLastName()),
                message.getTimestamp()
        );
    }
}