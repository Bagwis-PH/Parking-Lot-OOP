package Structure;

import Basics.ParkingSpotType;

public class HandicappedSpot extends ParkingSpot {
    public HandicappedSpot(String spotID) {
        super(spotID, ParkingSpotType.HANDICAPPED);
    }
}