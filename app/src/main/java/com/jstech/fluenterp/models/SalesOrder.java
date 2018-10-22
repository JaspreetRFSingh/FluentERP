package com.jstech.fluenterp.models;

public class SalesOrder {
    long salesDocNumber;
    int customerNumber;
    String orderDate;
    double orderPrice;
    String orderStatus;

    public SalesOrder(){

    }

    public SalesOrder(long salesDocNumber, int customerNumber, String orderDate, double orderPrice, String orderStatus) {
        this.salesDocNumber = salesDocNumber;
        this.customerNumber = customerNumber;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    @Override
    public String toString() {
        return "Sales Document Number: "+salesDocNumber+"\n\n"+
                "Customer: "+ customerNumber+"\n\n"+
                "Date of Order: "+ orderDate +"\n\n"+
                "Price: "+ orderPrice +"\n\n"+
                "Order Status: "+ orderStatus +"\n\n";
    }
}
