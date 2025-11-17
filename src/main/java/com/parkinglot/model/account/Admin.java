package com.parkinglot.model.account;

public class Admin extends Account {
    
    public Admin(String id, String name, String email) {
        super(id, name, email);
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
}
