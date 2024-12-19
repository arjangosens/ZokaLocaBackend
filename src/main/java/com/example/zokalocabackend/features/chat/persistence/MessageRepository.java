package com.example.zokalocabackend.features.chat.persistence;

import com.example.zokalocabackend.features.chat.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByConversationId(String conversationId);
    Optional<Message> findFirstByConversationIdOrderByTimestampDesc(String conversationId);
}
