package com.example.zokalocabackend.features.chat.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Conversation {
    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;

    @NotBlank
    private String name;

    private Set<String> branchIds;
}
