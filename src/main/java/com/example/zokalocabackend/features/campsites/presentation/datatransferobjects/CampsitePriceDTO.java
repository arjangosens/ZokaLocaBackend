package com.example.zokalocabackend.features.campsites.presentation.datatransferobjects;

import com.example.zokalocabackend.features.campsites.domain.PriceMode;
import jakarta.validation.constraints.Min;

public record CampsitePriceDTO(PriceMode priceMode, @Min(0) double amount) {
}
