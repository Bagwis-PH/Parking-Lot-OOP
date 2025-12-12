package Logic;

import Basics.ParkingConstants; // Ensure you have this or just use hardcoded 30

public class PricingStrategy {

    // 1. Standard Hourly Calculation (0-3 hrs rule, 12+ hrs rule)
    public static double calculateParkingFee(long hours, boolean isOvernight) {
        // RULE A: 12 Hours or Overnight = Flat 3000
        if (hours >= 12 || isOvernight) {
            return 3000.00;
        }

        // RULE B: Minimum 3 hours (Flat 30 pesos)
        if (hours <= 3) {
            return 30.00;
        } 
        
        // RULE C: Beyond 3 hours (30 base + 50 per extra hour)
        // Example: 5 hours = 30 + (2 * 50) = 130
        long extraHours = hours - 3;
        return 30.00 + (extraHours * 50.00);
    }

    // 2. Custom Quote Logic (Your New Request)
    public static double calculateCustomQuote(double sizeInSquareMeters) {
        double regularBasePrice = 30.00; 

        // LOGIC:
        // If size is 20 sqm or less -> Just Regular Price (30.00)
        // If size is > 20 sqm -> Regular Price + 10 Pesos per sqm
        
        if (sizeInSquareMeters <= 20.0) {
            return regularBasePrice;
        } else {
            // "You decide the pricing to how many will add php every sqm"
            // We add 10 pesos for every sqm
            double additionalCharge = sizeInSquareMeters * 10.0;
            return regularBasePrice + additionalCharge;
        }
    }
}