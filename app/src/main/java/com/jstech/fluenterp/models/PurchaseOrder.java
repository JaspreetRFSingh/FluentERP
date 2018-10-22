package com.jstech.fluenterp.models;

public class PurchaseOrder {
    long purchaseDocNumber;
    int sellerNumber;
    String orderDate;
    String orderStatus;

    public PurchaseOrder(){

    }

    public PurchaseOrder(long purchaseDocNumber, int sellerNumber, String orderDate, String orderStatus) {
        this.purchaseDocNumber = purchaseDocNumber;
        this.sellerNumber = sellerNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public long getPurchaseDocNumber() {
        return purchaseDocNumber;
    }

    public void setPurchaseDocNumber(long purchaseDocNumber) {
        this.purchaseDocNumber = purchaseDocNumber;
    }

    public int getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(int sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Purchase Document Number: "+purchaseDocNumber+"\n\n"+
                "Seller: "+ sellerNumber+"\n\n"+
                "Date of Order: "+ orderDate +"\n\n"+
                "Order Status: "+ orderStatus +"\n\n";
    }
}
