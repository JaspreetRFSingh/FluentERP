package com.jstech.fluenterp.models;

public class Seller {

    long sId;
    String sName;
    String sAddress;
    String sCity;
    String sGST;
    String sPhone;

    public Seller(){

    }

    public Seller(long sId, String sName, String sAddress, String sCity, String sGST, String sPhone) {
        this.sId = sId;
        this.sName = sName;
        this.sAddress = sAddress;
        this.sCity = sCity;
        this.sGST = sGST;
        this.sPhone = sPhone;
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public String getsGST() {
        return sGST;
    }

    public void setsGST(String sGST) {
        this.sGST = sGST;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }
}
