package Vehicles;
import Basics.VehicleType; 

public class Motorbike extends Vehicle {
    public Motorbike(String licensePlate) {
        // Automatically sets the type to MOTORBIKE
        super(licensePlate, VehicleType.MOTORBIKE);
    }
}

