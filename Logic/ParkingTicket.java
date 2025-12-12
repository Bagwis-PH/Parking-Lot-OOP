package Logic;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import Vehicles.Vehicle;

public class ParkingTicket {
    private static int idCounter = 1000; 
    private String ticketID;
    private Vehicle vehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalFee;
    private boolean isPaid;
    private boolean isOvernight; 

    public ParkingTicket(Vehicle vehicle) {
        this.ticketID = "TKT-" + idCounter++;
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now(); 
        this.isPaid = false;
        this.isOvernight = false;
    }

    // Call this method when the car tries to leave
    public void closeTicket() {
        this.exitTime = LocalDateTime.now();
        
        long hours = Duration.between(entryTime, exitTime).toHours();
        
        if (hours == 0) hours = 1; 
        
        this.totalFee = PricingStrategy.calculateParkingFee(hours, this.isOvernight);
    }
    
    // Helper to view ticket details nicely
    public String getTicketDetails() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String exitStr = (exitTime != null) ? exitTime.format(fmt) : "Still Parked";
        
        return "--- TICKET " + ticketID + " ---\n" +
               "Vehicle: " + vehicle.getLicensePlate() + "\n" +
               "Entry: " + entryTime.format(fmt) + "\n" +
               "Exit:  " + exitStr + "\n" +
               "Fee:   PHP " + totalFee + "\n" +
               "-----------------------";
    }

    // --- GETTERS AND SETTERS ---
    public String getTicketID() { return ticketID; }
    public double getTotalFee() { return totalFee; }
    public void setOvernight(boolean overnight) { this.isOvernight = overnight; }


    public Vehicle getVehicle() { 
        return vehicle; 
    }
    
    public void simulateDuration(double hoursToSimulate) {
        // We assume "hoursToSimulate" is how long they stayed.
        // So we set the Entry Time to (NOW minus HOURS).
        long minutes = (long) (hoursToSimulate * 60);
        this.entryTime = LocalDateTime.now().minusMinutes(minutes);
    }
}