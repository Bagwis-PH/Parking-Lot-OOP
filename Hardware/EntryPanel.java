package Hardware; //package Hardware

import Structure.ParkingLot;
import Structure.ParkingSpot;
import Vehicles.Vehicle;
import Logic.ParkingTicket;

public class EntryPanel {
    private String id;
    
    public EntryPanel(String id) {
        this.id = id;
    }

    // The main action: A car approaches and pushes the button
    public ParkingTicket printTicket(Vehicle vehicle, ParkingLot parkingLot) {
        System.out.println("Display: Welcome to " + parkingLot.getName());
        System.out.println("Action: Scanning vehicle " + vehicle.getLicensePlate() + "...");

        // 1. Find a spot
        ParkingSpot spot = parkingLot.findSpotForVehicle(vehicle.getType());

        if (spot == null) {
            System.out.println("Display: FULL. No spots available for " + vehicle.getType());
            return null;
        }

        // 2. Assign the car to the spot (Physically parking)
        spot.assignVehicle(vehicle);
        System.out.println("Display: Please go to Spot " + spot.getSpotID());

        // 3. Issue the Ticket (Start the timer)
        ParkingTicket ticket = new ParkingTicket(vehicle);
        return ticket;
    }
}