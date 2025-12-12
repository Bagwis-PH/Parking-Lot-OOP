package Logic;


public class PricingStrategy {

    public static double calculateParkingFee(long hours, boolean isOvernight) {
        // 12 Hours || overnight = 3000
        if (hours >= 12 || isOvernight) {
            return 3000.00;
        }

        // min 3 hours = 30
        if (hours <= 3) {
            return 30.00;
        } 
        
        // 3hrs ++ (30  + 50 per  hour)
        long extraHours = hours - 3;
        return 30.00 + (extraHours * 50.00);
    }

    // custom size quote 
    public static double calculateCustomQuote(double sizeInSquareMeters) {
        double regularBasePrice = 30.00; 

        // if size is 20 sqm or less = reg price (30.00)
        // if size is > 20 sqm = reg price + 10 Pesos per sqm
        
        if (sizeInSquareMeters <= 20.0) {
            return regularBasePrice;
        } else {}
            double additionalCharge = sizeInSquareMeters * 10.0;
            return regularBasePrice + additionalCharge;
        }
    }