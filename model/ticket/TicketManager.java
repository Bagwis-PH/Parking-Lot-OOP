package model.ticket;

import model.vehicle.Vehicle;
import model.parking.ParkingSlot; //need u guys to make the ParkingSlot class and other parts

public class TicketManager {

    private Ticket[] tickets;
    private int ticketCount;

    public TicketManager(int maxTickets) {
        this.tickets = new Ticket[maxTickets];
        this.ticketCount = 0;
    }

    // Create ticket
    public Ticket createTicket(String ticketId, Vehicle vehicle, ParkingSlot slot) {
        if (ticketCount >= tickets.length) {
            System.out.println("Ticket storage full.");
            return null;
        }

        Ticket t = new Ticket(ticketId, vehicle, slot);
        tickets[ticketCount] = t;
        ticketCount++;

        return t;
    }

    // Search by ticket ID
    public Ticket findTicketById(String ticketId) {
        for (int i = 0; i < ticketCount; i++) {
            Ticket t = tickets[i];

            if (t != null && t.getTicketId().equals(ticketId)) {
                return t;
            }
        }
        return null;
    }

    // Close ticket with price
    public boolean closeTicket(String ticketId, double finalPrice) {
        Ticket t = findTicketById(ticketId);

        if (t == null) {
            System.out.println("Ticket not found.");
            return false;
        }

        if (!t.isActive()) {
            System.out.println("Ticket already closed or cancelled.");
            return false;
        }

        t.close(finalPrice);
        return true;
    }

    // Cancel ticket
    public boolean cancelTicket(String ticketId) {
        Ticket t = findTicketById(ticketId);

        if (t == null) {
            System.out.println("Ticket not found.");
            return false;
        }

        if (!t.isActive()) {
            System.out.println("Ticket already closed.");
            return false;
        }

        t.cancel();
        return true;
    }

    // Viewing all tickets
    public void printAllTickets() {
        for (int i = 0; i < ticketCount; i++) {
            Ticket t = tickets[i];

            if (t != null) {
                System.out.println(
                    "ID: " + t.getTicketId() +
                    " | Status: " + t.getStatus() +
                    " | Plate: " + t.getVehicle().getPlateNumber() +
                    " | Price: " + t.getTotalPrice()
                );
            }
        }
    }
}
