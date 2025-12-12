package Accounts;

public class Admin extends Account {

    public Admin(String username, String password, Person person) {
        super(username, password, person); // Calls the constructor of the parent (Account)
    }

    // A method only Admins can do
    public void addParkingFloor(String floorName) {
        System.out.println("Admin " + getUsername() + " added floor: " + floorName);
        // We will hook this up to the actual logic later
    }

    public void addParkingSpot(String spotName) {
        System.out.println("Admin " + getUsername() + " added spot: " + spotName);
    }
}
