package com.example.zokalocabackend.campsites.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampsitePrice {

    @NotNull
    private PriceMode priceMode;

    @Min(0)
    private double amount;

    public CampsitePrice(PriceMode priceMode, double amount) {
        this.priceMode = priceMode;
        this.amount = amount;
    }
}
