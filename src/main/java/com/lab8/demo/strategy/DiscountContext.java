package com.lab8.demo.strategy;

import org.springframework.stereotype.Component;

@Component
public class DiscountContext {

    private final DiscountStrategy noDiscountStrategy;
    private final DiscountStrategy memberDiscountStrategy;
    private final DiscountStrategy seasonalSaleStrategy;

    public DiscountContext(NoDiscountStrategy noDiscountStrategy,MemberDiscountStrategy memberDiscountStrategy,SeasonalSaleStrategy seasonalSaleStrategy) {
        this.noDiscountStrategy = noDiscountStrategy;
        this.memberDiscountStrategy = memberDiscountStrategy;
        this.seasonalSaleStrategy = seasonalSaleStrategy;
    }

    public double calculatePrice(double originalPrice, String discountType) {
        DiscountStrategy strategy = resolve(discountType);
        return strategy.applyDiscount(originalPrice);
    }

    private DiscountStrategy resolve(String discountType) {
        if (discountType == null) {
            return noDiscountStrategy;
        }
        return switch (discountType.toUpperCase()) {
            case "MEMBER" -> memberDiscountStrategy;
            case "SEASONAL" -> seasonalSaleStrategy;
            default -> noDiscountStrategy;
        };
    }
}
