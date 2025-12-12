package Hardware;

import Logic.ParkingTicket;
import Structure.ParkingSpot;

public class ExitPanel {
    private final String id;

    public ExitPanel(String id) {
        this.id = id;
    }

    // User inserts ticket to pay
    public void processTicket(ParkingTicket ticket, ParkingSpot spot) {
        System.out.println("\nEXIT PANEL (" + id + ") ");
        
        // Close the ticket (calculates time and price)
        ticket.closeTicket();
        
        // Show the receipt
        System.out.println(ticket.getTicketDetails());
        
        // Clear the spot (Vehicle leaves)
        if (spot != null) {
            spot.removeVehicle();
            System.out.println("Gate: OPEN. Have a safe trip!");
        } else {
            System.out.println("Error: System could not verify spot location.");
        }
    }
}