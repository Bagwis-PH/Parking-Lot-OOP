package model.ticket;

import model.vehicle.Vehicle;
import model.parking.ParkingSlot; //need u guys to make the ParkingSlot class and other parts

public class Ticket {

    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;

    private double entryTime;       // beginner-safe type
    private double exitTime;        // beginner-safe type
    private double totalPrice;

    private TicketStatus status;    // switch to string if needed

    public Ticket() {
        this.ticketId = "";
        this.entryTime = 0.0;
        this.exitTime = 0.0;
        this.totalPrice = 0.0;
        this.status = TicketStatus.ACTIVE;
    }

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;

        this.entryTime = 0.0;   // GUI or controller can fill this in later
        this.exitTime = 0.0;
        this.totalPrice = 0.0;

        this.status = TicketStatus.ACTIVE;
    }

    // Getters & Setters 

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

    public double getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(double entryTime) {
        this.entryTime = entryTime;
    }

    public double getExitTime() {
        return exitTime;
    }

    public void setExitTime(double exitTime) {
        this.exitTime = exitTime;
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

    //  Helper Methods

    public boolean isActive() {
        return this.status == TicketStatus.ACTIVE;
    }

    public void close(double finalPrice) {
        this.totalPrice = finalPrice;
        this.status = TicketStatus.CLOSED;
    }

    public void cancel() {
        this.status = TicketStatus.CANCELLED;
    }
}
