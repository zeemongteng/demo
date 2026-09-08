package com.lab8.demo.strategy;

import org.springframework.stereotype.Component;

@Component
public class MemberDiscountStrategy implements DiscountStrategy{
    private static final double MEMBER_RATE = 0.10;
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - MEMBER_RATE);
    }
}
