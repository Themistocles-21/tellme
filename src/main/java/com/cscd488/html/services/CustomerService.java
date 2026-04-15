package com.cscd488.html.services;

import com.cscd488.html.model.*;
import com.cscd488.html.repository.CustomerRepository;
import com.cscd488.html.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public CustomerService(CustomerRepository customerRepository,
                           VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public void saveCustomer(Customer customer) {

        CustomerEntity entity = new CustomerEntity();
        entity.setFname(customer.getFname());
        entity.setLname(customer.getLname());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());

        customerRepository.save(entity);
    }

    public void saveVehicle(Vehicle vehicle) {

        vehicleRepository.save(vehicle);
    }
}