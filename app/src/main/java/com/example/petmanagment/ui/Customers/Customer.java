package com.example.petmanagment.ui.Customers;

import java.util.UUID;

public class Customer {
    private String name, lastName, phone, email, UUID;
    public Customer(){
        this.name = null;
        this.phone = null;
        this.lastName = null;
        this.email = null;
        this.UUID = java.util.UUID.randomUUID().toString();
    }
    public Customer(String name, String lastName, String phone, String email){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.UUID = java.util.UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUUID(){
        return this.UUID;
    }

    public String toString(){
        return String.format("%s, %s, %s", this.UUID, this.name, this.lastName);
    }
}
