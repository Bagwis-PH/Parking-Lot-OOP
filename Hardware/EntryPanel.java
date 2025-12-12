package Hardware; //package Hardware

import Logic.ParkingTicket;
import Structure.ParkingLot;
import Structure.ParkingSpot;
import Vehicles.Vehicle;

public class EntryPanel {   

    public EntryPanel(String string) {
    }

    // The main action: A car approaches and pushes the button
    public ParkingTicket printTicket(Vehicle vehicle, ParkingLot parkingLot) {
        System.out.println("Welcome to " + parkingLot.getName());
        System.out.println("Scanning vehicle " + vehicle.getLicensePlate() + "...");

        // Find a spot
        ParkingSpot spot = parkingLot.findSpotForVehicle(vehicle.getType());

        if (spot == null) {
            System.out.println("FULL. No spots available for " + vehicle.getType());
            return null;
        }

        // Assign the car to the spot (Physically parking)
        spot.assignVehicle(vehicle);
        System.out.println("Please go to Spot " + spot.getSpotID());

        // Issue the Ticket (Start the timer)
        ParkingTicket ticket = new ParkingTicket(vehicle);
        return ticket;
    }
}