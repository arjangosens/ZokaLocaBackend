package com.example.zokalocabackend.campsites.presentation.datatransferobjects;

import com.example.zokalocabackend.campsites.domain.PriceMode;

public record CampsitePriceDTO(PriceMode priceMode, double amount) {
}
