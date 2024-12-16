package com.example.zokalocabackend.features.chat.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

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

    private String authorId;

    private Instant timestamp;

    public Message(String id, String content, String conversationId, String authorId) {
        this.id = id;
        this.content = content;
        this.conversationId = conversationId;
        this.authorId = authorId;
        this.timestamp = Instant.now();
    }
}
