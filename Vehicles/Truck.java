package Vehicles;

import Basics.VehicleType; 

public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        // Now this will work because Vehicle.java is fixed!
        super(licensePlate, VehicleType.TRUCK);
    }
}