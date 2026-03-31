package com.cscd488.html.model;

import java.io.IOException;

public class Repository {

    FileWriter fileWriter = new FileWriter();

    public void infoGenerate(Customer customer, Vehicle vehicle) throws IOException {
        String custInfo = customer.toString();
        String vehInfo = vehicle.toString();
        fileWriter.writeToFile(custInfo + "\n" + vehInfo, "testText.txt");
    }
}