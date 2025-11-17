package com.parkinglot.model.pricing;

public class WidthBasedPricing implements PricingStrategy {
    private double baseRate;
    
    public WidthBasedPricing(double baseRate) {
        this.baseRate = baseRate;
    }
    
    @Override
    public double calculatePrice(double width, long durationInHours) {
        return width * baseRate * durationInHours;
    }
    
    @Override
    public String getStrategyName() {
        return "Width-Based Pricing";
    }
    
    public double getBaseRate() {
        return baseRate;
    }
    
    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }
}
