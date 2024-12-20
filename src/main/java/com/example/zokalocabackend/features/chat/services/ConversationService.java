package com.example.zokalocabackend.features.chat.services;

import com.example.zokalocabackend.features.chat.domain.Conversation;
import com.example.zokalocabackend.features.chat.persistence.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;

    public void createConversation(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    public Conversation getConversationById(String id) {
        return conversationRepository.findById(id).orElseThrow();
    }

    public List<Conversation> getAllConversationsByBranchId(String branchId) {
        return conversationRepository.findAllByBranchIdsContaining(branchId);
    }
}
