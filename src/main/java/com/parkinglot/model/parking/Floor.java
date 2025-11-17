package com.parkinglot.model.parking;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private List<ParkingSlot> slots;
    
    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }
    
    public int getFloorNumber() {
        return floorNumber;
    }
    
    public List<ParkingSlot> getSlots() {
        return slots;
    }
    
    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }
    
    public List<ParkingSlot> getAvailableSlots(double requestedWidth) {
        List<ParkingSlot> availableSlots = new ArrayList<>();
        for (ParkingSlot slot : slots) {
            if (slot.canFit(requestedWidth)) {
                availableSlots.add(slot);
            }
        }
        return availableSlots;
    }
    
    public ParkingSlot getSlotById(String slotId) {
        for (ParkingSlot slot : slots) {
            if (slot.getSlotId().equals(slotId)) {
                return slot;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return String.format("Floor %d (%d slots)", floorNumber, slots.size());
    }
}
