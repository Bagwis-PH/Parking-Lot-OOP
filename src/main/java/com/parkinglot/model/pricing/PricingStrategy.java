package com.parkinglot.model.pricing;

public interface PricingStrategy {
    double calculatePrice(double width, long durationInHours);
    String getStrategyName();
}
