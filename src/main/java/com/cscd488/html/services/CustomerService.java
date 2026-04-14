package com.cscd488.html.services;

import com.cscd488.html.model.*;
import com.cscd488.html.model.CustomerEntity;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerFileWriter fileWriter = new CustomerFileWriter();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // -------------------------
    // CUSTOMER SAVE
    // -------------------------
    public void saveCustomer(Customer customer) throws IOException {

        String text =
                "Customer Info\n" +
                        "First Name: " + customer.getFname() + "\n" +
                        "Last Name: " + customer.getLname() + "\n" +
                        "Email: " + customer.getEmail() + "\n" +
                        "Phone: " + customer.getPhone() + "\n" +
                        "Address: " + customer.getAddress();

        fileWriter.writeToFile(text, "customer.txt");

        CustomerEntity entity = new CustomerEntity();
        entity.setFname(customer.getFname());
        entity.setLname(customer.getLname());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());

        customerRepository.save(entity);
    }

    // -------------------------
    // VEHICLE SAVE (placeholder)
    // -------------------------
    public void saveVehicle(Vehicle vehicle) throws IOException {

        String text =
                "Vehicle Info\n" +
                        vehicle.toString();

        fileWriter.writeToFile(text, "vehicle.txt");

        // DB logic for vehicle will come next step
    }
}