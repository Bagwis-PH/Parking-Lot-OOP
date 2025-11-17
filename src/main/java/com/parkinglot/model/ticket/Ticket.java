package com.parkinglot.model.ticket;

import com.parkinglot.model.vehicle.Vehicle;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private String slotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double slotWidth;
    private double price;
    private boolean isPaid;
    
    public Ticket(Vehicle vehicle, String slotId, double slotWidth) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.slotId = slotId;
        this.slotWidth = slotWidth;
        this.entryTime = LocalDateTime.now();
        this.isPaid = false;
    }
    
    public String getTicketId() {
        return ticketId;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public String getSlotId() {
        return slotId;
    }
    
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    
    public LocalDateTime getExitTime() {
        return exitTime;
    }
    
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
    
    public double getSlotWidth() {
        return slotWidth;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    
    @Override
    public String toString() {
        return String.format("Ticket[ID: %s, Vehicle: %s, Slot: %s, Width: %.2f, Entry: %s]",
            ticketId.substring(0, 8), vehicle.getLicensePlate(), slotId, slotWidth, entryTime);
    }
}
