import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// Import all our packages
import Basics.*;
import Vehicles.*;
import Structure.*;
import Logic.*;
import Hardware.*;
import Accounts.*;

public class Main {
    
    // We keep a list of active tickets to simulate cars currently inside
    private static List<ParkingTicket> activeTickets = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    // Our System Components
    private static ParkingLot uscLot;
    private static EntryPanel entryPanel;
    private static ExitPanel exitPanel;
    private static Admin systemAdmin;

    public static void main(String[] args) {
        initializeSystem(); // Set up the lot, floors, and admin account

        while (true) {
            // THE MAIN MENU (Console Version of the Dashboard)
            System.out.println("\n==========================================");
            System.out.println("   USC PARKING SYSTEM (ADVANCED CONSOLE)  ");
            System.out.println("==========================================");
            System.out.println("1. [DRIVER] Enter Parking (Get Ticket)");
            System.out.println("2. [DRIVER] Pay & Exit");
            System.out.println("3. [DRIVER] Get Custom Quote / VIP Spot");
            System.out.println("4. [ADMIN]  Login to System");
            System.out.println("5. [INFO]   View Parking Capacity");
            System.out.println("6. Shutdown System");
            System.out.print("Select an option: ");

            String choice = scanner.next();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case "1":
                    handleEntry();
                    break;
                case "2":
                    handleExit();
                    break;
                case "3":
                    handleCustomQuote();
                    break;
                case "4":
                    handleAdminLogin();
                    break;
                case "5":
                    showCapacity();
                    break;
                case "6":
                    System.out.println("Shutting down...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- SETUP: Auto-Generate Spots like the GUI ---
    private static void initializeSystem() {
        // 1. Create Structure
        uscLot = new ParkingLot("USC Mega Complex");
        
        // Floor 1: Cars (2 Rows x 10 Cols = 20 Spots)
        ParkingFloor carFloor = new ParkingFloor("Level 1 (Cars)");
        generateSpots(carFloor, 20, "C", ParkingSpotType.COMPACT);
        uscLot.addFloor(carFloor);

        // Floor 2: Motorbikes (2 Rows x 15 Cols = 30 Spots)
        ParkingFloor bikeFloor = new ParkingFloor("Level 2 (Bikes)");
        generateSpots(bikeFloor, 30, "M", ParkingSpotType.MOTORBIKE);
        uscLot.addFloor(bikeFloor);

        // Floor 3: Trucks (2 Rows x 5 Cols = 10 Spots)
        ParkingFloor truckFloor = new ParkingFloor("Level 3 (Trucks)");
        generateSpots(truckFloor, 10, "L", ParkingSpotType.LARGE);
        uscLot.addFloor(truckFloor);

        // 2. Create Hardware
        entryPanel = new EntryPanel("ENTRY-01");
        exitPanel = new ExitPanel("EXIT-01");

        // 3. Create Admin Account 
        Address adminAddr = new Address("Talamban Campus", "Cebu City", "6000", "Philippines");
        Person adminPerson = new Person("Chief Administrator", adminAddr, "admin@usc.edu.ph", "0917-000-0000");
        systemAdmin = new Admin("admin", "password123", adminPerson);
    }

    // Helper to generate spots automatically
    private static void generateSpots(ParkingFloor floor, int count, String prefix, ParkingSpotType type) {
        for (int i = 1; i <= count; i++) {
            String id = prefix + "-" + i;
            if (type == ParkingSpotType.COMPACT) floor.addSpot(new CompactSpot(id));
            else if (type == ParkingSpotType.MOTORBIKE) floor.addSpot(new MotorbikeSpot(id));
            else floor.addSpot(new LargeSpot(id));
        }
    }

    // --- FEATURE 1: ENTER PARKING (With Time Simulation) ---
    private static void handleEntry() {
        System.out.println("\n--- ENTRY TERMINAL ---");
        System.out.print("Enter Driver Name: ");
        String name = scanner.nextLine();

        System.out.println("Select Vehicle Type:");
        System.out.println("1. Car (Compact)");
        System.out.println("2. Truck/Van (Large)");
        System.out.println("3. Motorbike");
        System.out.print("Choice: ");
        
        int typeChoice = scanner.nextInt();
        Vehicle vehicle = null;
        String plate = "PLT-" + (int)(Math.random() * 9999); 

        switch (typeChoice) {
            case 1: vehicle = new Car(plate); break;
            case 2: vehicle = new Truck(plate); break;
            case 3: vehicle = new Motorbike(plate); break;
            default: System.out.println("Invalid type."); return;
        }

        // --- SIMULATION FEATURE ---
        // We ask for hours here so you can test the 3000 fine logic instantly
        System.out.print("Simulate duration? (Enter hours, e.g., 12 for fine, 0 for now): ");
        double simHours = scanner.nextDouble();

        System.out.println("Processing...");
        
        // Use the Entry Panel logic
        ParkingTicket ticket = entryPanel.printTicket(vehicle, uscLot);
        
        if (ticket != null) {
            // Apply simulation if user entered hours
            if (simHours > 0) {
                ticket.simulateDuration(simHours);
                if (simHours >= 24) ticket.setOvernight(true);
                System.out.println("(System: Time warped " + simHours + " hours back)");
            }

            activeTickets.add(ticket);
            System.out.println(">> Welcome, " + name + "!"); 
            System.out.println(">> Ticket Issued: " + ticket.getTicketID());
        }
    }

    // --- FEATURE 2: EXIT & PAY (With Updated Pricing Rules) ---
    private static void handleExit() {
        System.out.println("\n--- EXIT TERMINAL ---");
        if (activeTickets.isEmpty()) {
            System.out.println("No vehicles are currently parked.");
            return;
        }

        // List Tickets
        System.out.println("Select your Ticket ID to pay:");
        for (int i = 0; i < activeTickets.size(); i++) {
            ParkingTicket t = activeTickets.get(i);
            // Show Ticket ID + Vehicle Plate
            System.out.println((i + 1) + ". " + t.getTicketID() + " [" + t.getVehicle().getLicensePlate() + "]");
        }
        System.out.print("Choice: ");
        int choice = scanner.nextInt();
        
        if (choice < 1 || choice > activeTickets.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        ParkingTicket ticketToPay = activeTickets.get(choice - 1);
        
        // Calculate Fee (Uses logic: <3hrs=30, >12hrs=3000)
        ticketToPay.closeTicket(); 
        
        System.out.println("\n-----------------------------");
        System.out.println("   PAYMENT REQUIRED");
        System.out.println("-----------------------------");
        System.out.println("Ticket: " + ticketToPay.getTicketID());
        System.out.println("Total Amount Due: PHP " + ticketToPay.getTotalFee());
        System.out.println("-----------------------------");
        
        System.out.print("Confirm Payment? (yes/no): ");
        String pay = scanner.next();
        
        if (pay.equalsIgnoreCase("yes")) {
            // Find spot and free it
            ParkingSpot spot = uscLot.findSpotForVehicle(ticketToPay.getVehicle().getType());
            // Note: findSpotForVehicle finds FREE spots. 
            // In a real DB we'd look up occupied spots. For console demo, we just remove ticket.
            
            // Call Exit Panel
            exitPanel.processTicket(ticketToPay, spot);
            activeTickets.remove(ticketToPay);
        } else {
            System.out.println("Payment cancelled.");
        }
    }

    // --- FEATURE 3: CUSTOM QUOTE (With >20sqm Logic) ---
    private static void handleCustomQuote() {
        System.out.println("\n--- CUSTOM / VIP QUOTE ---");
        System.out.print("Enter vehicle size (sq meters): ");
        double size = scanner.nextDouble();
        
        double price = PricingStrategy.calculateCustomQuote(size);
        
        System.out.println(">> QUOTE RESULT:");
        if (size <= 20) {
            System.out.println("   Size is standard (<= 20sqm).");
            System.out.println("   Price: PHP " + price + " (Base Rate)");
        } else {
            System.out.println("   Size is large (> 20sqm).");
            System.out.println("   Price: PHP " + price + " (Includes surcharge of 10/sqm)");
        }

        System.out.print("Do you want to book this VIP spot? (yes/no): ");
        String book = scanner.next();
        
        if (book.equalsIgnoreCase("yes")) {
            scanner.nextLine(); // consume newline
            System.out.print("Enter Driver Name: ");
            String name = scanner.nextLine();
            
            // Create a "fake" custom ticket
            Vehicle vipVehicle = new Truck("VIP-" + (int)(Math.random()*100));
            ParkingTicket vipTicket = new ParkingTicket(vipVehicle);
            activeTickets.add(vipTicket);
            
            System.out.println(">> VIP Spot Reserved for " + name + ".");
            System.out.println(">> Ticket " + vipTicket.getTicketID() + " issued.");
        }
    }

    // --- FEATURE 4: ADMIN LOGIN ---
    private static void handleAdminLogin() {
        System.out.println("\n--- ADMIN LOGIN ---");
        System.out.print("Username: ");
        String user = scanner.next();
        System.out.print("Password: ");
        String pass = scanner.next();

        if (systemAdmin.login(user, pass)) {
            System.out.println("\n>> LOGIN SUCCESSFUL <<");
            System.out.println("Welcome, " + systemAdmin.getPerson().getName());
            System.out.println("Email: " + systemAdmin.getPerson().getEmail());
            System.out.println("Address: " + systemAdmin.getPerson().getAddress().toString());
            
            System.out.println("\n[Admin Actions]");
            System.out.println("- Current Active Tickets: " + activeTickets.size());
            System.out.println("Press Enter to logout...");
            scanner.nextLine(); // consume previous enter
            scanner.nextLine(); // wait for enter
        } else {
            System.out.println(">> ERROR: Invalid Credentials.");
        }
    }

    // --- FEATURE 5: SHOW CAPACITY ---
    private static void showCapacity() {
        System.out.println("\n--- LOT STATUS ---");
        System.out.println("Active Vehicles: " + activeTickets.size());
        // Since we generated specific numbers:
        System.out.println("Total Capacity: 60 Spots (plus VIP)");
        System.out.println("Levels: 3 Floors");
    }
}
