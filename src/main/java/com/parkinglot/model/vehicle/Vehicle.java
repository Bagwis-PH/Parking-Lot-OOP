package com.parkinglot.model.vehicle;

public class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private double width;
    
    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.width = type.getDefaultWidth();
    }
    
    public Vehicle(String licensePlate, VehicleType type, double width) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.width = width;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public VehicleType getType() {
        return type;
    }
    
    public double getWidth() {
        return width;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s, width: %.2f)", licensePlate, type, width);
    }
}
