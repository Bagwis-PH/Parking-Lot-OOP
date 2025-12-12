package Vehicles;

import Basics.VehicleType; 

public class Car extends Vehicle {
    public Car(String licensePlate) {
        // Automatically sets the type to CAR
        super(licensePlate, VehicleType.CAR);
    }
}
