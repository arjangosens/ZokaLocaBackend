package com.example.zokalocabackend.features.chat.domain;

import com.example.zokalocabackend.features.usermanagement.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;

    @NotBlank
    private String content;

    @NotBlank
    private String conversationId;

    @DBRef
    private User author;

    private Instant timestamp;

    public Message(String id, String content, String conversationId, User author) {
        this.id = id;
        this.content = content;
        this.conversationId = conversationId;
        this.author = author;
        this.timestamp = Instant.now();
    }
}
