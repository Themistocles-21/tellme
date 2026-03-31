package com.cscd488.html.model;

public class Vehicle {

    private String make;
    private String model;
    private String year;
    private String vin;
    private String freeFormText;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getFreeFormText() {
        return freeFormText;
    }

    public void setFreeFormText(String freeFormText) {
        this.freeFormText = freeFormText;
    }

    @Override
    public String toString() {
        return "\n\nBelow is Vehicle information:\nMake: " + make +
                "\nModel: " + model +
                "\nYear: " + year +
                "\nVIN: " + vin +
                "\nTechnical Issue: " + freeFormText;
    }
}
