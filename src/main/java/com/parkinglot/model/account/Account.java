package com.parkinglot.model.account;

public abstract class Account {
    private String id;
    private String name;
    private String email;
    
    public Account(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public abstract String getRole();
    
    @Override
    public String toString() {
        return String.format("%s (ID: %s, Email: %s)", name, id, email);
    }
}
