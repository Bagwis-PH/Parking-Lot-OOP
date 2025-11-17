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

/**
 * Demonstration of the Parking Lot System features
 * Shows the complete workflow including custom width slot finding
 */
public class ParkingLotDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   PARKING LOT SYSTEM - FEATURE DEMONSTRATION              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Initialize the parking lot
        WidthBasedPricing pricing = new WidthBasedPricing(10.0);
        ParkingLot parkingLot = new ParkingLot("City Center Parking", pricing);
        
        // Setup parking structure
        System.out.println("\n1. Setting up parking structure...");
        setupParkingStructure(parkingLot);
        System.out.println("   ✓ Created 3 floors with various slot widths");
        
        // Demonstrate findAvailableSlotsForCustomWidth
        demonstrateFindSlotsByWidth(parkingLot);
        
        // Demonstrate parking and pricing
        demonstrateParkingAndPricing(parkingLot);
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   DEMONSTRATION COMPLETED SUCCESSFULLY                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private static void setupParkingStructure(ParkingLot parkingLot) {
        for (int i = 1; i <= 3; i++) {
            Floor floor = new Floor(i);
            floor.addSlot(new ParkingSlot("F" + i + "-S1", 0.5));  // Motorcycle
            floor.addSlot(new ParkingSlot("F" + i + "-S2", 1.0));  // Compact/Sedan
            floor.addSlot(new ParkingSlot("F" + i + "-S3", 1.5));  // SUV
            floor.addSlot(new ParkingSlot("F" + i + "-S4", 2.0));  // Truck
            parkingLot.addFloor(floor);
        }
    }
    
    private static void demonstrateFindSlotsByWidth(ParkingLot parkingLot) {
        System.out.println("\n2. Demonstrating findAvailableSlotsForCustomWidth()...");
        System.out.println("   ────────────────────────────────────────────────────");
        
        // Test different width requirements
        double[] widths = {0.5, 1.0, 1.5, 2.0};
        
        for (double width : widths) {
            List<ParkingSlot> slots = parkingLot.findAvailableSlotsForCustomWidth(width);
            System.out.printf("\n   Searching for slots with width >= %.1f:\n", width);
            System.out.printf("   ✓ Found %d available slots\n", slots.size());
            
            if (!slots.isEmpty()) {
                System.out.println("   Sample slots:");
                for (int i = 0; i < Math.min(3, slots.size()); i++) {
                    ParkingSlot slot = slots.get(i);
                    double pricePerHour = slot.getWidth() * 
                        ((WidthBasedPricing) parkingLot.getPricingStrategy()).getBaseRate();
                    System.out.printf("     • %s - $%.2f/hour\n", 
                        slot.getSlotId(), pricePerHour);
                }
            }
        }
    }
    
    private static void demonstrateParkingAndPricing(ParkingLot parkingLot) {
        System.out.println("\n3. Demonstrating parking and width-based pricing...");
        System.out.println("   ────────────────────────────────────────────────────");
        
        try {
            // Scenario 1: Park a motorcycle (small vehicle)
            System.out.println("\n   Scenario 1: Parking a Motorcycle");
            Vehicle motorcycle = new Vehicle("BIKE001", VehicleType.MOTORCYCLE);
            List<ParkingSlot> bikeSots = parkingLot.findAvailableSlotsForCustomWidth(0.5);
            if (!bikeSots.isEmpty()) {
                Ticket ticket1 = parkingLot.parkVehicle(motorcycle, bikeSots.get(0).getSlotId());
                System.out.printf("   ✓ Parked in slot: %s\n", ticket1.getSlotId());
                System.out.printf("   ✓ Rate: $%.2f/hour (%.1f width × $10 base rate)\n",
                    ticket1.getSlotWidth() * 10, ticket1.getSlotWidth());
            }
            
            // Scenario 2: Park an SUV (larger vehicle)
            System.out.println("\n   Scenario 2: Parking an SUV");
            Vehicle suv = new Vehicle("SUV999", VehicleType.SUV);
            List<ParkingSlot> suvSlots = parkingLot.findAvailableSlotsForCustomWidth(1.5);
            if (!suvSlots.isEmpty()) {
                Ticket ticket2 = parkingLot.parkVehicle(suv, suvSlots.get(0).getSlotId());
                System.out.printf("   ✓ Parked in slot: %s\n", ticket2.getSlotId());
                System.out.printf("   ✓ Rate: $%.2f/hour (%.1f width × $10 base rate)\n",
                    ticket2.getSlotWidth() * 10, ticket2.getSlotWidth());
                
                // Exit the SUV to show price calculation
                System.out.println("\n   Processing exit...");
                Ticket exitTicket = parkingLot.exitVehicle(ticket2.getTicketId());
                System.out.printf("   ✓ Total charge: $%.2f\n", exitTicket.getPrice());
                System.out.printf("   ✓ Calculation: width (%.1f) × base rate ($10) × duration (%d hour)\n",
                    exitTicket.getSlotWidth(), 1);
            }
            
            // Scenario 3: Try to find slots for very wide truck
            System.out.println("\n   Scenario 3: Finding slots for a wide truck (2.0 width)");
            List<ParkingSlot> truckSlots = parkingLot.findAvailableSlotsForCustomWidth(2.0);
            System.out.printf("   ✓ Found %d suitable slots\n", truckSlots.size());
            for (ParkingSlot slot : truckSlots) {
                System.out.printf("     • %s (width: %.1f) - $%.2f/hour\n",
                    slot.getSlotId(), slot.getWidth(), slot.getWidth() * 10);
            }
            
        } catch (NoAvailableSlotException | InvalidTicketException e) {
            System.err.println("   ✗ Error: " + e.getMessage());
        }
    }
}
