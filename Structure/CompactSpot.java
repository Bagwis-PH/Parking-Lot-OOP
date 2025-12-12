package Structure;

import Basics.ParkingSpotType;

public class CompactSpot extends ParkingSpot {
    public CompactSpot(String spotID) {
        super(spotID, ParkingSpotType.COMPACT);
    }
}