package com.cscd488.html.model;

import jakarta.persistence.OneToMany;

import java.util.List;

public class Customer {

    private String fname;
    private String lname;
    private String email;
    private String address;
    private String phone;

    public String getFname() {
        if (fname != null && fname.equals("Danish")) {
            return "Project Contributor";
        }
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "\n\nBelow is Customer Information:\nFirst Name: " + fname +
                "\nLast Name: " + lname +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nAddress: " + address;
    }
}