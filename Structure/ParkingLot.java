package Structure;

import java.util.ArrayList;
import java.util.List;
import Basics.VehicleType;

public class ParkingLot {
    private String name;
    private List<ParkingFloor> floors;

    public ParkingLot(String name) {
        this.name = name;
        this.floors = new ArrayList<>();
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    // The EntryPanel will call this to ask "Is there space?"
    public ParkingSpot findSpotForVehicle(VehicleType type) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findFreeSpot(type);
            if (spot != null) {
                return spot;
            }
        }
        return null; // Lot is full for this type
    }
    
    public String getName() { return name; }
}