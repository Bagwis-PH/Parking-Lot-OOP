package Structure;

import Basics.ParkingSpotType;
import Vehicles.Vehicle;

public abstract class ParkingSpot {
    private String spotID;
    private ParkingSpotType type;
    private boolean isFree;
    private Vehicle vehicle; // The vehicle currently parked here

    public ParkingSpot(String spotID, ParkingSpotType type) {
        this.spotID = spotID;
        this.type = type;
        this.isFree = true;
    }

    public boolean assignVehicle(Vehicle vehicle) {
        if (isFree) {
            this.vehicle = vehicle;
            this.isFree = false;
            return true; // Successfully parked
        }
        return false; // Spot is already taken
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isFree = true;
    }

    // Getters
    public String getSpotID() { return spotID; }
    public ParkingSpotType getType() { return type; }
    public boolean isFree() { return isFree; }
    public Vehicle getVehicle() { return vehicle; }
}