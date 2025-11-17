#!/bin/bash
# Demo script for Parking Lot System

echo "Building the Parking Lot Application..."
javac -d bin -sourcepath src/main/java src/main/java/com/parkinglot/**/*.java src/main/java/com/parkinglot/*.java

echo ""
echo "Running Tests..."
javac -d bin -cp bin -sourcepath src/test/java src/test/java/com/parkinglot/*.java
java -cp bin com.parkinglot.ParkingLotTest

echo ""
echo "======================================"
echo "To run the interactive application:"
echo "======================================"
echo "java -cp bin com.parkinglot.ParkingLotApplication"
echo ""
echo "Main features:"
echo "1. Find Available Slots by Custom Width - Search for slots that fit your vehicle"
echo "2. Park Vehicle - Park in a specific slot"
echo "3. Exit Vehicle - Calculate and pay parking fee"
echo "4. Display All Slots - View parking lot status"
echo "5. Display Active Tickets - View current parking tickets"
