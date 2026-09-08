package com.lab8.demo.strategy;

import org.springframework.stereotype.Component;

@Component
public class SeasonalSaleStrategy implements DiscountStrategy{
    private static final double SEASONAL_RATE = 0.20;
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - SEASONAL_RATE);
    }
}