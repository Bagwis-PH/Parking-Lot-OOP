package Structure;

import Basics.ParkingSpotType;

public class LargeSpot extends ParkingSpot {
    public LargeSpot(String spotID) {
        super(spotID, ParkingSpotType.LARGE);
    }
}