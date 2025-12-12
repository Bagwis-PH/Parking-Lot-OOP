package Hardware;

import Logic.ParkingTicket;
import Structure.ParkingSpot;

public class ExitPanel {
    private String id;

    public ExitPanel(String id) {
        this.id = id;
    }

    // The main action: User inserts ticket to pay
    public void processTicket(ParkingTicket ticket, ParkingSpot spot) {
        System.out.println("\n--- EXIT PANEL (" + id + ") ---");
        
        // 1. Close the ticket (calculates time and price)
        ticket.closeTicket();
        
        // 2. Show the receipt
        System.out.println(ticket.getTicketDetails());
        
        // 3. Clear the spot (Vehicle leaves)
        if (spot != null) {
            spot.removeVehicle();
            System.out.println("Gate: OPEN. Have a safe trip!");
        } else {
            System.out.println("Error: System could not verify spot location.");
        }
    }
}