package com.example.petmanagment.ui.Customers;

public class Customer {
    private String name, lastName, phone, email;
    public Customer(){
        this.name = null;
        this.phone = null;
        this.lastName = null;
        this.email = null;
    }
    public Customer(String name, String lastName, String phone, String email){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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

    public String toString(){
        return this.name+this.lastName+this.email+this.phone;
    }
}
