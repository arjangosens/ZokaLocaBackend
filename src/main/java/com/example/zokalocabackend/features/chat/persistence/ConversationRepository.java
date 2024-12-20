package com.example.zokalocabackend.features.chat.persistence;

import com.example.zokalocabackend.features.chat.domain.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
    List<Conversation> findAllByBranchIdsContaining(String branchId);
}
