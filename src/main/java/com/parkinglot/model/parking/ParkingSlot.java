package com.parkinglot.model.parking;

import com.parkinglot.model.vehicle.Vehicle;

public class ParkingSlot {
    private String slotId;
    private double width;
    private boolean isOccupied;
    private Vehicle parkedVehicle;
    
    public ParkingSlot(String slotId, double width) {
        this.slotId = slotId;
        this.width = width;
        this.isOccupied = false;
    }
    
    public String getSlotId() {
        return slotId;
    }
    
    public double getWidth() {
        return width;
    }
    
    public boolean isOccupied() {
        return isOccupied;
    }
    
    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }
    
    public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
    }
    
    public void removeVehicle() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }
    
    public boolean canFit(double requestedWidth) {
        return !isOccupied && width >= requestedWidth;
    }
    
    @Override
    public String toString() {
        String status = isOccupied ? "Occupied by " + parkedVehicle.getLicensePlate() : "Available";
        return String.format("Slot %s (Width: %.2f) - %s", slotId, width, status);
    }
}
