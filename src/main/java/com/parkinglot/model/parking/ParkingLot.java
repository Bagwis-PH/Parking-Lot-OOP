package com.parkinglot.model.parking;

import com.parkinglot.exception.NoAvailableSlotException;
import com.parkinglot.exception.InvalidTicketException;
import com.parkinglot.model.ticket.Ticket;
import com.parkinglot.model.vehicle.Vehicle;
import com.parkinglot.model.pricing.PricingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private String name;
    private List<Floor> floors;
    private Map<String, Ticket> activeTickets;
    private PricingStrategy pricingStrategy;
    
    public ParkingLot(String name, PricingStrategy pricingStrategy) {
        this.name = name;
        this.floors = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.pricingStrategy = pricingStrategy;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Floor> getFloors() {
        return floors;
    }
    
    public void addFloor(Floor floor) {
        floors.add(floor);
    }
    
    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }
    
    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
    
    /**
     * Find all available slots that can accommodate a vehicle with the requested width.
     * Scans all floors and returns slots where slot.width >= requestedWidth.
     * 
     * @param requestedWidth The minimum width required
     * @return List of available parking slots that can fit the requested width
     */
    public List<ParkingSlot> findAvailableSlotsForCustomWidth(double requestedWidth) {
        List<ParkingSlot> availableSlots = new ArrayList<>();
        
        for (Floor floor : floors) {
            List<ParkingSlot> floorSlots = floor.getAvailableSlots(requestedWidth);
            availableSlots.addAll(floorSlots);
        }
        
        return availableSlots;
    }
    
    /**
     * Park a vehicle in a specific slot identified by slotId.
     * 
     * @param vehicle The vehicle to park
     * @param slotId The ID of the slot to park in
     * @return The parking ticket
     * @throws NoAvailableSlotException if the slot is not available
     */
    public Ticket parkVehicle(Vehicle vehicle, String slotId) throws NoAvailableSlotException {
        ParkingSlot slot = findSlotById(slotId);
        
        if (slot == null) {
            throw new NoAvailableSlotException("Slot " + slotId + " does not exist");
        }
        
        if (slot.isOccupied()) {
            throw new NoAvailableSlotException("Slot " + slotId + " is already occupied");
        }
        
        if (!slot.canFit(vehicle.getWidth())) {
            throw new NoAvailableSlotException("Slot " + slotId + " is too small for this vehicle");
        }
        
        slot.parkVehicle(vehicle);
        Ticket ticket = new Ticket(vehicle, slotId, slot.getWidth());
        activeTickets.put(ticket.getTicketId(), ticket);
        
        return ticket;
    }
    
    /**
     * Process exit and calculate price based on width-based pricing.
     * 
     * @param ticketId The ticket ID
     * @return The final ticket with calculated price
     * @throws InvalidTicketException if ticket is invalid
     */
    public Ticket exitVehicle(String ticketId) throws InvalidTicketException {
        Ticket ticket = activeTickets.get(ticketId);
        
        if (ticket == null) {
            throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
        }
        
        ticket.setExitTime(LocalDateTime.now());
        
        long durationInHours = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        if (durationInHours < 1) {
            durationInHours = 1; // Minimum 1 hour
        }
        
        double price = pricingStrategy.calculatePrice(ticket.getSlotWidth(), durationInHours);
        ticket.setPrice(price);
        
        ParkingSlot slot = findSlotById(ticket.getSlotId());
        if (slot != null) {
            slot.removeVehicle();
        }
        
        activeTickets.remove(ticketId);
        ticket.setPaid(true);
        
        return ticket;
    }
    
    private ParkingSlot findSlotById(String slotId) {
        for (Floor floor : floors) {
            ParkingSlot slot = floor.getSlotById(slotId);
            if (slot != null) {
                return slot;
            }
        }
        return null;
    }
    
    public Map<String, Ticket> getActiveTickets() {
        return activeTickets;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%d floors, %d active tickets)", 
            name, floors.size(), activeTickets.size());
    }
}
