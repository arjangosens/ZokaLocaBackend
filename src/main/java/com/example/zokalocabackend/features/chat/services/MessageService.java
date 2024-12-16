package com.example.zokalocabackend.features.chat.services;

import com.example.zokalocabackend.features.chat.domain.Message;
import com.example.zokalocabackend.features.chat.persistence.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessagesByConversationId(String conversationId) {
        return messageRepository.findAllByConversationId(conversationId);
    }
}