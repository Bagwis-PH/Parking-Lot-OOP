package Accounts;

public class Address {
    private String streetAddress;
    private String city;
    private String zipCode;
    private String country;

    public Address(String streetAddress, String city, String zipCode, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    // Getters and Setters
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    // A helper method to print the full address nicely
    @Override
    public String toString() {
        return streetAddress + ", " + city + " " + zipCode + ", " + country;
    }
}