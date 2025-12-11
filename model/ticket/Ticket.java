package model.ticket;

import model.vehicle.Vehicle; //need u guys to make the Vehicle class and package
import model.parking.ParkingSlot; //also this

public class Ticket {

    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;

    private long entryTimeMillis;
    private long exitTimeMillis;

    private double totalPrice;
    private TicketStatus status;

    // Default constructor (optional but safe to have)
    public Ticket() {
        this.status = TicketStatus.ACTIVE;
        this.entryTimeMillis = System.currentTimeMillis();
    }

    // Main constructor used when creating a ticket
    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryTimeMillis = System.currentTimeMillis();
        this.status = TicketStatus.ACTIVE;
    }

    // Getters and setters

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public long getEntryTimeMillis() {
        return entryTimeMillis;
    }

    public void setEntryTimeMillis(long entryTimeMillis) {
        this.entryTimeMillis = entryTimeMillis;
    }

    public long getExitTimeMillis() {
        return exitTimeMillis;
    }

    public void setExitTimeMillis(long exitTimeMillis) {
        this.exitTimeMillis = exitTimeMillis;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    // Helper methods

    public boolean isActive() {
        return this.status == TicketStatus.ACTIVE;
    }

    public long getDurationMillis() {
        long end;

        if (status == TicketStatus.ACTIVE) {
            end = System.currentTimeMillis();
        } else {
            end = exitTimeMillis;
        }

        return end - entryTimeMillis;
    }

    public void close(double finalPrice) {
        this.exitTimeMillis = System.currentTimeMillis();
        this.totalPrice = finalPrice;
        this.status = TicketStatus.CLOSED;
    }

    public void cancel() {
        this.status = TicketStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId +
               ", Vehicle Plate: " + (vehicle != null ? vehicle.getPlateNumber() : "N/A") +
               ", Slot: " + (parkingSlot != null ? parkingSlot.getSlotId() : "N/A") +
               ", Status: " + status +
               ", Total Price: " + totalPrice;
    }
}
