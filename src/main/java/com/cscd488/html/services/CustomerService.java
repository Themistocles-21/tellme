package com.cscd488.html.services;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    public Customer findByEmail(String email) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void saveCustomer(Customer customer) {
        System.out.println("Saving customer info...");
        entityManager.persist(customer);
    }

    @Transactional
    public void saveVehicle(Vehicle vehicle) {
        System.out.println("Saving vehicle info...");
        entityManager.persist(vehicle);
    }
}