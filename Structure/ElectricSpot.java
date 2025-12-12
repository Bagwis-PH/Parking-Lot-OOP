package Structure;

import Basics.ParkingSpotType;

public class ElectricSpot extends ParkingSpot {
    public ElectricSpot(String spotID) {
        super(spotID, ParkingSpotType.ELECTRIC);
    }
}