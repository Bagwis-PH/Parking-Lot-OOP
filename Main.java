public class Main {
    public static void main(String[] args) {
        System.out.println("--- TESTING STEP 1: BASICS ---");

        // 1. Test ParkingConstants
        System.out.println("\n[Testing Constants]");
        System.out.println("Base Rate should be 30.0: " + ParkingConstants.BASE_RATE_PER_HOUR);
        System.out.println("Overnight Fine should be 3000.0: " + ParkingConstants.OVERNIGHT_FINE);
        
        // Simple logic check
        double testPrice = ParkingConstants.BASE_RATE_PER_HOUR * 2;
        System.out.println("Cost for 2 hours (Manual Calc): " + testPrice + " PHP");

        // 2. Test VehicleType Enum
        System.out.println("\n[Testing VehicleType Enum]");
        VehicleType myCar = VehicleType.CAR;
        VehicleType myBike = VehicleType.MOTORBIKE;
        
        System.out.println("Selected Vehicle: " + myCar);
        System.out.println("Selected Vehicle: " + myBike);

        // 3. Test ParkingSpotType Enum
        System.out.println("\n[Testing ParkingSpotType Enum]");
        for (ParkingSpotType type : ParkingSpotType.values()) {
            System.out.println("Available Spot Type: " + type);
        }

        System.out.println("\n--- TEST COMPLETE ---");
    }
}