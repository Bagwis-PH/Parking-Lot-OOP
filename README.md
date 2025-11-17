# Parking-Lot-OOP

A Java-based Parking Lot Management System designed for modularity and scalability. The project follows a multi-package structure to handle exceptions, various user accounts, parking management, ticketing, pricing strategies, and different vehicle types.

## Features

- **Custom Width Slot Finder**: Find available parking slots based on vehicle width requirements
- **Width-Based Pricing**: Dynamic pricing based on slot width (price = width × base_rate × duration)
- **Multi-Floor Support**: Manage parking across multiple floors
- **Vehicle Management**: Support for various vehicle types (Motorcycle, Compact, Sedan, SUV, Truck)
- **Ticket System**: Generate and manage parking tickets with entry/exit times
- **Interactive CLI**: User-friendly command-line interface for all operations

## Project Structure

```
src/
├── main/java/com/parkinglot/
│   ├── exception/              # Custom exception classes
│   │   ├── ParkingException.java
│   │   ├── NoAvailableSlotException.java
│   │   └── InvalidTicketException.java
│   ├── model/
│   │   ├── account/            # User account classes
│   │   │   ├── Account.java
│   │   │   ├── Admin.java
│   │   │   └── Customer.java
│   │   ├── parking/            # Parking management classes
│   │   │   ├── ParkingLot.java
│   │   │   ├── Floor.java
│   │   │   └── ParkingSlot.java
│   │   ├── pricing/            # Pricing strategy classes
│   │   │   ├── PricingStrategy.java
│   │   │   └── WidthBasedPricing.java
│   │   ├── ticket/             # Ticket management
│   │   │   └── Ticket.java
│   │   └── vehicle/            # Vehicle classes
│   │       ├── Vehicle.java
│   │       └── VehicleType.java
│   └── ParkingLotApplication.java  # Main application
└── test/java/com/parkinglot/
    └── ParkingLotTest.java     # Test suite
```

## Building and Running

### Compile the Application

```bash
javac -d bin -sourcepath src/main/java src/main/java/com/parkinglot/**/*.java src/main/java/com/parkinglot/*.java
```

### Run Tests

```bash
javac -d bin -cp bin -sourcepath src/test/java src/test/java/com/parkinglot/*.java
java -cp bin com.parkinglot.ParkingLotTest
```

### Run the Application

```bash
java -cp bin com.parkinglot.ParkingLotApplication
```

## Usage

The application provides an interactive menu with the following options:

1. **Find Available Slots by Custom Width**: Search for parking slots that can accommodate vehicles of a specific width
2. **Park Vehicle**: Park a vehicle in a specific slot
3. **Exit Vehicle**: Process vehicle exit and calculate parking fees
4. **Display All Slots**: View all parking slots and their status
5. **Display Active Tickets**: View all currently active parking tickets
6. **Exit Application**: Close the application

### Example: Finding Slots by Custom Width

1. Select option 1 from the main menu
2. Enter the required width (e.g., 1.5 for an SUV)
3. The system displays all available slots with width >= 1.5
4. Each slot shows its price per hour (width × $10 base rate)
5. Select a slot to proceed with parking

## Key Implementation Details

### findAvailableSlotsForCustomWidth Method

Located in `ParkingLot.java`, this method:
- Scans all floors in the parking lot
- Filters slots where `slot.width >= requestedWidth`
- Returns only available (non-occupied) slots
- Enables users to select from a list of suitable slots

### Width-Based Pricing

The `WidthBasedPricing` strategy calculates fees as:
```
price = slotWidth × baseRate × durationInHours
```

Default base rate: $10 per width unit per hour

### Slot Widths

- Motorcycle: 0.5 units
- Compact/Sedan: 1.0 units
- SUV: 1.5 units
- Truck: 2.0 units

## Testing

Run the test suite to verify:
- Custom width slot finding functionality
- Width-based pricing calculations
- Park and exit operations
- Slot availability updates

All tests should pass with checkmarks (✓) indicating successful validation.
