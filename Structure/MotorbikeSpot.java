package Structure;

import Basics.ParkingSpotType;

public class MotorbikeSpot extends ParkingSpot {
    public MotorbikeSpot(String spotID) {
        super(spotID, ParkingSpotType.MOTORBIKE);
    }
}