package com.parkinglot.model.account;

public class Customer extends Account {
    
    public Customer(String id, String name, String email) {
        super(id, name, email);
    }
    
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
