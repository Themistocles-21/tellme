package com.cscd488.html.services;

import com.cscd488.html.model.*;
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

    public void saveCustomer(Customer customer) throws IOException {

        CustomerEntity entity = new CustomerEntity();
        entity.setFname(customer.getFname());
        entity.setLname(customer.getLname());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());

        customerRepository.save(entity);
    }

    public void saveVehicle(Vehicle vehicle, String customerEmail) throws IOException {

        vehicle.setCustomerEmail(customerEmail);

        vehicleRepository.save(vehicle);
    }
}