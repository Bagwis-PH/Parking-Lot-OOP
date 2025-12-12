package model.parking;

import model.vehicle.Vehicle;

public class CustomSlotSelector {

    // select slots based on requested width
    public ParkingSlot[] findSlotsByWidth(ParkingLot lot, double requestedWidth) {

        // count total slots in all floors (1st)
        int totalSlots = 0;
        ParkingFloor[] floors = lot.getFloors();

        for (int i = 0; i < lot.getFloorCount(); i++) {
            ParkingFloor floor = floors[i];
            if (floor != null) {
                totalSlots += floor.getSlotCount();
            }
        }

        // make a temporary storage for all matching slots (2nd)
        ParkingSlot[] tempMatches = new ParkingSlot[totalSlots];
        int matchCount = 0;

        // scan each floor and each slot (3rd)
        for (int i = 0; i < lot.getFloorCount(); i++) {
            ParkingFloor floor = floors[i];

            if (floor == null) {
                continue;
            }

            ParkingSlot[] slots = floor.getSlots();
            int count = floor.getSlotCount();

            for (int j = 0; j < count; j++) {
                ParkingSlot slot = slots[j];

                // Conditions:
                // 1) slot exists
                // 2) slot is not occupied
                // 3) slot width >= requested width
                if (slot != null &&
                    !slot.isOccupied() &&
                    slot.getWidth() >= requestedWidth) {

                    tempMatches[matchCount] = slot;
                    matchCount++;
                }
            }
        }

        // Copy valid matches into a properly-sized array (4th)
        ParkingSlot[] finalSlots = new ParkingSlot[matchCount];
        for (int i = 0; i < matchCount; i++) {
            finalSlots[i] = tempMatches[i];
        }

        return finalSlots;
    }
}
