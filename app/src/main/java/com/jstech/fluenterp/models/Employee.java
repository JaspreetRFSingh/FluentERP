package com.jstech.fluenterp.models;

public class Employee {
    int empId;
    String empName;
    String empAddress;
    String empType;
    long empPhone;
    String dob;
    String doj;

    public Employee(){

    }

    public Employee(int empId, String empName, String empAddress, String empType, long empPhone, String dob, String doj) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empType = empType;
        this.empPhone = empPhone;
        this.dob = dob;
        this.doj = doj;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public long getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(long empPhone) {
        this.empPhone = empPhone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    @Override
    public String toString() {
        return getEmpId()+ " - " + getEmpName()+"\n"
                +"\nType: "+getEmpType()+"\n"
                +"\nPhone: "+getEmpPhone()+"\n"
                +"\nAddress: "+getEmpAddress()+"\n"
                +"\nDate of Birth: "+getDob()+"\n"
                +"\nDate of Joining: " +getDoj();
    }
}
