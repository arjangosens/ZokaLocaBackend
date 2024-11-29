package com.example.zokalocabackend.features.assetmanagement.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Asset {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String contentType;

    @NotBlank
    private AssetLocation location;
}
