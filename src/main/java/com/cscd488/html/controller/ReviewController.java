package com.cscd488.html.controller;

import com.cscd488.html.model.*;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@SessionAttributes({"customer", "vehicle"})
public class ReviewController {

    private final CustomerService customerService;

    public ReviewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/review")
    public String finalize(@ModelAttribute("customer") Customer customer,
                           @ModelAttribute("vehicle") Vehicle vehicle,
                           HttpSession session,
                           Model model) {

        // DEBUG (IMPORTANT)
        System.out.println("CUSTOMER: " + customer.getEmail());
        System.out.println("VEHICLE: " + vehicle.getVin());

        // SAVE ONCE
        CustomerEntity savedCustomer = customerService.saveCustomer(customer);
        vehicle.setCustomer(savedCustomer);
        customerService.saveVehicle(vehicle);

        // CLEAR SESSION
        session.invalidate();

        // CONFIRMATION DATA
        model.addAttribute("confirmationMsg", "Success");
        model.addAttribute("orderNumber", UUID.randomUUID().toString().substring(0, 8));
        model.addAttribute("dateTime", LocalDateTime.now().toString());
        model.addAttribute("email", customer.getEmail());

        return "confirmation";
    }
}