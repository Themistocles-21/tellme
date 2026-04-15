package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class ReviewController {

    private final CustomerService customerService;

    public ReviewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/confirmationPage")
    public String confirm(@ModelAttribute Customer customer,
                          @ModelAttribute Vehicle vehicle,
                          Model model) throws IOException {

        // Link vehicle to customer
        vehicle.setCustomer(customer);

        // Check if customer already exists by email
        Customer existingCustomer = customerService.findByEmail(customer.getEmail());

        if (existingCustomer != null) {
            // Use existing customer instead of creating new one
            vehicle.setCustomer(existingCustomer);
            customerService.saveVehicle(vehicle);
        } else {
            // Save new customer and vehicle
            customerService.saveCustomer(customer);
            customerService.saveVehicle(vehicle);
        }

        // Generate order number
        String orderNumber = UUID.randomUUID().toString().substring(0, 8);

        // Add to model for display
        Customer displayCustomer = (existingCustomer != null) ? existingCustomer : customer;
        model.addAttribute("customer", displayCustomer);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("confirmationMsg", "Your order was successfully submitted!");
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("dateTime", LocalDateTime.now().toString());
        model.addAttribute("email", displayCustomer.getEmail());
        model.addAttribute("msgToReadEmail", "Please check your inbox and make sure email is not marked as spam.");

        return "confirmationPage";
    }
}