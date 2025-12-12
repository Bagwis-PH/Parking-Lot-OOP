package model.vehicle;

public class Vehicle {

    private String plateNumber;
    private double width;     // used for slot selection
    private String type;      // "Car", "Motorcycle", "Truck", etc.

    // Default constructor
    public Vehicle() {
        this.plateNumber = "";
        this.width = 0.0;
        this.type = "";
    }

    // Main constructor
    public Vehicle(String plateNumber, double width, String type) {
        this.plateNumber = plateNumber;
        this.width = width;
        this.type = type;
    }

    // Getters and setters

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Helper: check if this vehicle can fit in a slot width
    public boolean canFit(double slotWidth) {
        return this.width <= slotWidth;
    }
}
