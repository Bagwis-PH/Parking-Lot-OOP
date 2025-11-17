package com.parkinglot;

import com.parkinglot.exception.NoAvailableSlotException;
import com.parkinglot.exception.InvalidTicketException;
import com.parkinglot.model.parking.Floor;
import com.parkinglot.model.parking.ParkingLot;
import com.parkinglot.model.parking.ParkingSlot;
import com.parkinglot.model.pricing.WidthBasedPricing;
import com.parkinglot.model.ticket.Ticket;
import com.parkinglot.model.vehicle.Vehicle;
import com.parkinglot.model.vehicle.VehicleType;

import java.util.List;
import java.util.Scanner;

public class ParkingLotApplication {
    private ParkingLot parkingLot;
    private Scanner scanner;
    
    public ParkingLotApplication() {
        // Initialize parking lot with width-based pricing (base rate: $10 per width unit per hour)
        WidthBasedPricing pricingStrategy = new WidthBasedPricing(10.0);
        parkingLot = new ParkingLot("City Center Parking", pricingStrategy);
        scanner = new Scanner(System.in);
        
        // Initialize parking structure
        initializeParkingStructure();
    }
    
    private void initializeParkingStructure() {
        // Create 3 floors
        for (int i = 1; i <= 3; i++) {
            Floor floor = new Floor(i);
            
            // Add various width slots to each floor
            floor.addSlot(new ParkingSlot("F" + i + "-S1", 0.5));  // Motorcycle
            floor.addSlot(new ParkingSlot("F" + i + "-S2", 0.5));  // Motorcycle
            floor.addSlot(new ParkingSlot("F" + i + "-S3", 1.0));  // Compact/Sedan
            floor.addSlot(new ParkingSlot("F" + i + "-S4", 1.0));  // Compact/Sedan
            floor.addSlot(new ParkingSlot("F" + i + "-S5", 1.5));  // SUV
            floor.addSlot(new ParkingSlot("F" + i + "-S6", 2.0));  // Truck
            
            parkingLot.addFloor(floor);
        }
    }
    
    public void run() {
        System.out.println("===========================================");
        System.out.println("  Welcome to " + parkingLot.getName());
        System.out.println("===========================================");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    findSlotsByCustomWidth();
                    break;
                case 2:
                    parkVehicle();
                    break;
                case 3:
                    exitVehicle();
                    break;
                case 4:
                    displayAllSlots();
                    break;
                case 5:
                    displayActiveTickets();
                    break;
                case 6:
                    System.out.println("\nThank you for using our parking system!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n===========================================");
        System.out.println("            MAIN MENU");
        System.out.println("===========================================");
        System.out.println("1. Find Available Slots by Custom Width");
        System.out.println("2. Park Vehicle");
        System.out.println("3. Exit Vehicle");
        System.out.println("4. Display All Slots");
        System.out.println("5. Display Active Tickets");
        System.out.println("6. Exit Application");
        System.out.print("\nEnter your choice: ");
    }
    
    private void findSlotsByCustomWidth() {
        System.out.println("\n--- Find Available Slots by Custom Width ---");
        System.out.print("Enter requested width (e.g., 0.5, 1.0, 1.5, 2.0): ");
        double requestedWidth = getDoubleInput();
        
        List<ParkingSlot> availableSlots = parkingLot.findAvailableSlotsForCustomWidth(requestedWidth);
        
        if (availableSlots.isEmpty()) {
            System.out.println("\nNo available slots found for width >= " + requestedWidth);
            return;
        }
        
        System.out.println("\nAvailable slots for width >= " + requestedWidth + ":");
        System.out.println("===========================================");
        for (int i = 0; i < availableSlots.size(); i++) {
            ParkingSlot slot = availableSlots.get(i);
            WidthBasedPricing pricing = (WidthBasedPricing) parkingLot.getPricingStrategy();
            double pricePerHour = slot.getWidth() * pricing.getBaseRate();
            System.out.printf("%d. %s - Price: $%.2f/hour\n", 
                i + 1, slot, pricePerHour);
        }
        
        System.out.print("\nSelect a slot to park (enter number, 0 to cancel): ");
        int selection = getIntInput();
        
        if (selection == 0) {
            System.out.println("Selection cancelled.");
            return;
        }
        
        if (selection < 1 || selection > availableSlots.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        ParkingSlot selectedSlot = availableSlots.get(selection - 1);
        System.out.println("\nYou selected: " + selectedSlot);
        System.out.println("Now proceed to park your vehicle in this slot.");
        
        // Prompt to park vehicle
        parkVehicleInSlot(selectedSlot.getSlotId());
    }
    
    private void parkVehicle() {
        System.out.println("\n--- Park Vehicle ---");
        System.out.print("Enter slot ID (e.g., F1-S1): ");
        String slotId = scanner.nextLine().trim();
        
        parkVehicleInSlot(slotId);
    }
    
    private void parkVehicleInSlot(String slotId) {
        System.out.print("Enter vehicle license plate: ");
        String licensePlate = scanner.nextLine().trim();
        
        System.out.println("\nSelect vehicle type:");
        System.out.println("1. MOTORCYCLE (default width: 0.5)");
        System.out.println("2. COMPACT (default width: 1.0)");
        System.out.println("3. SEDAN (default width: 1.0)");
        System.out.println("4. SUV (default width: 1.5)");
        System.out.println("5. TRUCK (default width: 2.0)");
        System.out.print("Enter choice: ");
        int typeChoice = getIntInput();
        
        VehicleType type;
        switch (typeChoice) {
            case 1: type = VehicleType.MOTORCYCLE; break;
            case 2: type = VehicleType.COMPACT; break;
            case 3: type = VehicleType.SEDAN; break;
            case 4: type = VehicleType.SUV; break;
            case 5: type = VehicleType.TRUCK; break;
            default:
                System.out.println("Invalid vehicle type.");
                return;
        }
        
        System.out.print("Use default width? (y/n): ");
        String useDefault = scanner.nextLine().trim().toLowerCase();
        
        Vehicle vehicle;
        if (useDefault.equals("y")) {
            vehicle = new Vehicle(licensePlate, type);
        } else {
            System.out.print("Enter custom width: ");
            double customWidth = getDoubleInput();
            vehicle = new Vehicle(licensePlate, type, customWidth);
        }
        
        try {
            Ticket ticket = parkingLot.parkVehicle(vehicle, slotId);
            System.out.println("\n✓ Vehicle parked successfully!");
            System.out.println(ticket);
            System.out.printf("Parking rate: $%.2f per hour (Width: %.2f × Base rate: $%.2f)\n",
                ticket.getSlotWidth() * ((WidthBasedPricing) parkingLot.getPricingStrategy()).getBaseRate(),
                ticket.getSlotWidth(),
                ((WidthBasedPricing) parkingLot.getPricingStrategy()).getBaseRate());
        } catch (NoAvailableSlotException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    private void exitVehicle() {
        System.out.println("\n--- Exit Vehicle ---");
        
        if (parkingLot.getActiveTickets().isEmpty()) {
            System.out.println("No vehicles currently parked.");
            return;
        }
        
        System.out.println("\nActive Tickets:");
        int index = 1;
        for (Ticket ticket : parkingLot.getActiveTickets().values()) {
            System.out.printf("%d. %s\n", index++, ticket);
        }
        
        System.out.print("\nEnter ticket ID (or first 8 characters): ");
        String ticketIdInput = scanner.nextLine().trim();
        
        // Find matching ticket
        String fullTicketId = null;
        for (String ticketId : parkingLot.getActiveTickets().keySet()) {
            if (ticketId.startsWith(ticketIdInput) || ticketId.equals(ticketIdInput)) {
                fullTicketId = ticketId;
                break;
            }
        }
        
        if (fullTicketId == null) {
            System.out.println("Ticket not found.");
            return;
        }
        
        try {
            Ticket ticket = parkingLot.exitVehicle(fullTicketId);
            System.out.println("\n✓ Vehicle exited successfully!");
            System.out.println(ticket);
            System.out.printf("Total Price: $%.2f\n", ticket.getPrice());
            System.out.printf("(Width: %.2f × Base rate: $%.2f × Duration: %d hours)\n",
                ticket.getSlotWidth(),
                ((WidthBasedPricing) parkingLot.getPricingStrategy()).getBaseRate(),
                java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours() < 1 ? 1 :
                java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours());
        } catch (InvalidTicketException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }
    
    private void displayAllSlots() {
        System.out.println("\n--- All Parking Slots ---");
        for (Floor floor : parkingLot.getFloors()) {
            System.out.println("\n" + floor + ":");
            for (ParkingSlot slot : floor.getSlots()) {
                System.out.println("  " + slot);
            }
        }
    }
    
    private void displayActiveTickets() {
        System.out.println("\n--- Active Tickets ---");
        
        if (parkingLot.getActiveTickets().isEmpty()) {
            System.out.println("No active tickets.");
            return;
        }
        
        for (Ticket ticket : parkingLot.getActiveTickets().values()) {
            System.out.println(ticket);
        }
    }
    
    private int getIntInput() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private double getDoubleInput() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    public static void main(String[] args) {
        ParkingLotApplication app = new ParkingLotApplication();
        app.run();
    }
}
