package com.jstech.fluenterp.models;

public class Customer {
    private int customerId;
    public String name;
    public String address;
    private String city;
    public int phone;
    private String gstNumber;

    public Customer()
    {

    }

    public Customer(int customerId, String name, String address, String city, int phone, String gstNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.gstNumber = gstNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    @Override
    public String toString() {
        return "Customer: " + getCustomerId() + "\n\n" +
                "Name: " + getName() + "\n\n" +
                "Address: " + getAddress() + "\n\n" +
                "City: " + getCity() + "\n\n" +
                "Contact: " + getPhone() + "\n\n" +
                "GST No.: " + getGstNumber();
    }
}
