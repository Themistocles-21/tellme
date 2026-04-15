package com.cscd488.html.services;

import com.cscd488.html.model.*;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.repository.CustomerRepository;
import com.cscd488.html.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerFileWriter fileWriter = new CustomerFileWriter();

    public CustomerService(CustomerRepository customerRepository,
                           VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public void saveVehicle(Vehicle vehicle, Long customerId) throws IOException {

        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        vehicle.setCustomer(customer);

        fileWriter.writeToFile("Vehicle Info\n" + vehicle.toString(), "vehicle.txt");

        vehicleRepository.save(vehicle);
    }
}