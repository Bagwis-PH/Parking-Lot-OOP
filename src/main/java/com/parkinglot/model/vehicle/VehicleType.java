package com.parkinglot.model.vehicle;

public enum VehicleType {
    MOTORCYCLE(0.5),
    COMPACT(1.0),
    SEDAN(1.0),
    SUV(1.5),
    TRUCK(2.0);
    
    private final double defaultWidth;
    
    VehicleType(double defaultWidth) {
        this.defaultWidth = defaultWidth;
    }
    
    public double getDefaultWidth() {
        return defaultWidth;
    }
}
