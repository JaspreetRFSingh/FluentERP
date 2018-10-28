package com.jstech.fluenterp.models;

public class Material {

    int materialCode;
    String materialType;
    String materialDescription;
    String dimensionalUnit;
    double costPerDu;

    public Material(int materialCode, String materialType, String materialDescription, String dimensionalUnit, double costPerDu) {
        this.materialCode = materialCode;
        this.materialType = materialType;
        this.materialDescription = materialDescription;
        this.dimensionalUnit = dimensionalUnit;
        this.costPerDu = costPerDu;
    }
    public Material()
    {

    }

    public int getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(int materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getDimensionalUnit() {
        return dimensionalUnit;
    }

    public void setDimensionalUnit(String dimensionalUnit) {
        this.dimensionalUnit = dimensionalUnit;
    }

    public double getCostPerDu() {
        return costPerDu;
    }

    public void setCostPerDu(double costPerDu) {
        this.costPerDu = costPerDu;
    }

    @Override
    public String toString() {
        return "Code: " + materialCode+"\n"+
                "Type: "+materialType+"\n"+
                "Description: "+materialDescription+"\n"+
                "Unit: "+dimensionalUnit+"\n"+
                "Cost per DU: "+costPerDu+"\n";
    }
}
