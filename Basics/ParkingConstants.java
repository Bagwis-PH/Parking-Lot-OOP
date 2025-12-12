package Basics;

public class ParkingConstants {
    // Basic Pricing
    public static final double BASE_RATE_PER_HOUR = 30.00;
    
    // Pricing Strategy Rules
    public static final int MAX_REGULAR_HOURS = 6;
    public static final double SURCHARGE_PER_HOUR = 50.00; // Added after 6 hours
    
    // Fines
    public static final double OVERNIGHT_FINE = 3000.00;

    // Custom Quote Multiplier (Example: size * this rate)
    // We will use this for your "Custom Pick" feature later
    public static final double CUSTOM_SIZE_RATE_MULTIPLIER = 10.0; 
}
