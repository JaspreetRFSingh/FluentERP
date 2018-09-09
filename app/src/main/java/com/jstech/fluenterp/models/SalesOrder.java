package com.jstech.fluenterp.models;

public class SalesOrder {
    long salesDocNumber;
    int customerNumber;
    String orderDate;
    double orderPrice;

    public SalesOrder(){

    }

    public SalesOrder(long salesDocNumber, int customerNumber, String orderDate, double orderPrice) {
        this.salesDocNumber = salesDocNumber;
        this.customerNumber = customerNumber;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
    }

    public long getSalesDocNumber() {
        return salesDocNumber;
    }

    public void setSalesDocNumber(long salesDocNumber) {
        this.salesDocNumber = salesDocNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
}
