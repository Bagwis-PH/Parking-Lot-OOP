package Vehicles;
import Basics.VehicleType; 

public class ElectricCar extends Vehicle {
    public ElectricCar(String licensePlate) {
        // Automatically sets the type to ELECTRIC
        super(licensePlate, VehicleType.ELECTRIC);
    }
}