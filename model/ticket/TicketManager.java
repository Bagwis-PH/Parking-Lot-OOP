package model.ticket;

import model.vehicle.Vehicle; //need u guys to make the Vehicle class and package
import model.parking.ParkingSlot; //also this

public class TicketManager {

    private Ticket[] tickets;
    private int ticketCount;

    public TicketManager(int maxTickets) {
        this.tickets = new Ticket[maxTickets];
        this.ticketCount = 0;
    }

    // Create and store a new ticket
    public Ticket createTicket(String ticketId, Vehicle vehicle, ParkingSlot slot) {
        if (ticketCount >= tickets.length) {
            System.out.println("No more tickets can be stored.");
            return null;
        }

        Ticket ticket = new Ticket(ticketId, vehicle, slot);
        tickets[ticketCount] = ticket;
        ticketCount++;

        return ticket;
    }

    // Find ticket by ID (linear search)
    public Ticket findTicketById(String ticketId) {
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i] != null && tickets[i].getTicketId().equals(ticketId)) {
                return tickets[i];
            }
        }
        return null;
    }

    // Close ticket with a given price (price can come from pricing logic)
    public boolean closeTicket(String ticketId, double finalPrice) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket not found: " + ticketId);
            return false;
        }

        if (!ticket.isActive()) {
            System.out.println("Ticket is not active: " + ticketId);
            return false;
        }

        ticket.close(finalPrice);
        return true;
    }

    // Optional: cancel ticket
    public boolean cancelTicket(String ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket not found: " + ticketId);
            return false;
        }

        if (!ticket.isActive()) {
            System.out.println("Ticket is not active: " + ticketId);
            return false;
        }

        ticket.cancel();
        return true;
    }

    // Optional: print all tickets (for debugging)
    public void printAllTickets() {
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i] != null) {
                System.out.println(tickets[i].toString());
            }
        }
    }
}
