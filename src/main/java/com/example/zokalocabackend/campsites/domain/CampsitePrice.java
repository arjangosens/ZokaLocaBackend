package com.example.zokalocabackend.campsites.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampsitePrice {
    private PriceMode priceMode;
    private double amount;

    public CampsitePrice(PriceMode priceMode, double amount) {
        this.priceMode = priceMode;
        this.amount = amount;
    }
}
