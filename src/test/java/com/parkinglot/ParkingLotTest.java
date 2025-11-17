package com.parkinglot;

import com.parkinglot.exception.NoAvailableSlotException;
import com.parkinglot.model.parking.Floor;
import com.parkinglot.model.parking.ParkingLot;
import com.parkinglot.model.parking.ParkingSlot;
import com.parkinglot.model.pricing.WidthBasedPricing;
import com.parkinglot.model.ticket.Ticket;
import com.parkinglot.model.vehicle.Vehicle;
import com.parkinglot.model.vehicle.VehicleType;

import java.util.List;

public class ParkingLotTest {
    
    public static void main(String[] args) {
        System.out.println("Running Parking Lot System Tests...\n");
        
        testFindAvailableSlotsForCustomWidth();
        testWidthBasedPricing();
        testParkAndExit();
        
        System.out.println("\nAll tests completed!");
    }
    
    public static void testFindAvailableSlotsForCustomWidth() {
        System.out.println("=== Test: Find Available Slots For Custom Width ===");
        
        WidthBasedPricing pricing = new WidthBasedPricing(10.0);
        ParkingLot parkingLot = new ParkingLot("Test Parking", pricing);
        
        // Create floor with various width slots
        Floor floor1 = new Floor(1);
        floor1.addSlot(new ParkingSlot("F1-S1", 0.5));  // Motorcycle
        floor1.addSlot(new ParkingSlot("F1-S2", 1.0));  // Compact
        floor1.addSlot(new ParkingSlot("F1-S3", 1.5));  // SUV
        floor1.addSlot(new ParkingSlot("F1-S4", 2.0));  // Truck
        
        Floor floor2 = new Floor(2);
        floor2.addSlot(new ParkingSlot("F2-S1", 1.0));
        floor2.addSlot(new ParkingSlot("F2-S2", 1.5));
        
        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);
        
        // Test 1: Find slots for width 1.0
        List<ParkingSlot> slotsFor1_0 = parkingLot.findAvailableSlotsForCustomWidth(1.0);
        assert slotsFor1_0.size() == 4 : "Should find 4 slots with width >= 1.0";
        System.out.println("✓ Found " + slotsFor1_0.size() + " slots for width >= 1.0");
        
        // Test 2: Find slots for width 1.5
        List<ParkingSlot> slotsFor1_5 = parkingLot.findAvailableSlotsForCustomWidth(1.5);
        assert slotsFor1_5.size() == 3 : "Should find 3 slots with width >= 1.5";
        System.out.println("✓ Found " + slotsFor1_5.size() + " slots for width >= 1.5");
        
        // Test 3: Find slots for width 2.0
        List<ParkingSlot> slotsFor2_0 = parkingLot.findAvailableSlotsForCustomWidth(2.0);
        assert slotsFor2_0.size() == 1 : "Should find 1 slot with width >= 2.0";
        System.out.println("✓ Found " + slotsFor2_0.size() + " slots for width >= 2.0");
        
        // Test 4: Find slots for width 0.5
        List<ParkingSlot> slotsFor0_5 = parkingLot.findAvailableSlotsForCustomWidth(0.5);
        assert slotsFor0_5.size() == 6 : "Should find all 6 slots for width >= 0.5";
        System.out.println("✓ Found " + slotsFor0_5.size() + " slots for width >= 0.5");
        
        // Test 5: Park a vehicle and verify it's excluded from search
        try {
            Vehicle vehicle = new Vehicle("ABC123", VehicleType.COMPACT);
            parkingLot.parkVehicle(vehicle, "F1-S2");
            
            List<ParkingSlot> slotsAfterParking = parkingLot.findAvailableSlotsForCustomWidth(1.0);
            assert slotsAfterParking.size() == 3 : "Should find 3 slots after parking one";
            System.out.println("✓ Correctly excluded occupied slot from search");
        } catch (NoAvailableSlotException e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    public static void testWidthBasedPricing() {
        System.out.println("=== Test: Width-Based Pricing ===");
        
        WidthBasedPricing pricing = new WidthBasedPricing(10.0);
        
        // Test price calculation
        double price1 = pricing.calculatePrice(1.0, 2);  // 1.0 width * 10 base * 2 hours
        assert price1 == 20.0 : "Price should be 20.0";
        System.out.println("✓ Price for width 1.0, 2 hours: $" + price1);
        
        double price2 = pricing.calculatePrice(1.5, 3);  // 1.5 width * 10 base * 3 hours
        assert price2 == 45.0 : "Price should be 45.0";
        System.out.println("✓ Price for width 1.5, 3 hours: $" + price2);
        
        double price3 = pricing.calculatePrice(2.0, 1);  // 2.0 width * 10 base * 1 hour
        assert price3 == 20.0 : "Price should be 20.0";
        System.out.println("✓ Price for width 2.0, 1 hour: $" + price3);
        
        System.out.println();
    }
    
    public static void testParkAndExit() {
        System.out.println("=== Test: Park and Exit with Price Calculation ===");
        
        WidthBasedPricing pricing = new WidthBasedPricing(10.0);
        ParkingLot parkingLot = new ParkingLot("Test Parking", pricing);
        
        Floor floor = new Floor(1);
        floor.addSlot(new ParkingSlot("F1-S1", 1.5));
        parkingLot.addFloor(floor);
        
        try {
            // Park vehicle
            Vehicle vehicle = new Vehicle("XYZ789", VehicleType.SUV, 1.3);
            Ticket ticket = parkingLot.parkVehicle(vehicle, "F1-S1");
            
            assert ticket != null : "Ticket should not be null";
            assert ticket.getSlotWidth() == 1.5 : "Slot width should be 1.5";
            System.out.println("✓ Vehicle parked successfully");
            System.out.println("  Ticket: " + ticket);
            
            // Simulate some time passing (in real scenario)
            // For testing, we'll immediately exit
            
            // Exit vehicle
            Ticket exitTicket = parkingLot.exitVehicle(ticket.getTicketId());
            
            assert exitTicket.isPaid() : "Ticket should be marked as paid";
            assert exitTicket.getPrice() >= 15.0 : "Price should be at least $15 (1.5 * 10 * 1 hour minimum)";
            System.out.println("✓ Vehicle exited successfully");
            System.out.println("  Final price: $" + exitTicket.getPrice());
            
            // Verify slot is now available
            List<ParkingSlot> availableSlots = parkingLot.findAvailableSlotsForCustomWidth(1.0);
            assert availableSlots.size() == 1 : "Slot should be available again";
            System.out.println("✓ Slot is available again after exit");
            
        } catch (Exception e) {
            System.out.println("✗ Test failed: " + e.getMessage());
        }
        
        System.out.println();
    }
}
