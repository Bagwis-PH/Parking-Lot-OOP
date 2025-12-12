package Structure;

import java.util.ArrayList;
import java.util.List;
import Basics.VehicleType;
import Basics.ParkingSpotType;

public class ParkingFloor {
    private String floorID;
    private List<ParkingSpot> spots;

    public ParkingFloor(String floorID) {
        this.floorID = floorID;
        this.spots = new ArrayList<>();
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    // This method finds a free spot for a specific vehicle type on this floor
    public ParkingSpot findFreeSpot(VehicleType vehicleType) {
        for (ParkingSpot spot : spots) {
            if (spot.isFree()) {
                // Logic: Does this spot fit this vehicle?
                // For simplicity: Compact fits Car, Large fits Truck, etc.
                
                // You can add complex matching logic here (e.g., Car can park in Large spot)
                // For now, we do exact matching or simple rules:
                if (vehicleType == VehicleType.CAR && spot.getType() == ParkingSpotType.COMPACT) return spot;
                if (vehicleType == VehicleType.TRUCK && spot.getType() == ParkingSpotType.LARGE) return spot;
                if (vehicleType == VehicleType.MOTORBIKE && spot.getType() == ParkingSpotType.MOTORBIKE) return spot;
                if (vehicleType == VehicleType.ELECTRIC && spot.getType() == ParkingSpotType.ELECTRIC) return spot;
            }
        }
        return null; // No spot found on this floor
    }
}