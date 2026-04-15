package com.cscd488.html.controller;

import com.cscd488.html.model.*;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class ReviewController {

    private final CustomerService customerService;

    public ReviewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/review")
    public String finalSubmit(@ModelAttribute Customer customer,
                              @ModelAttribute Vehicle vehicle,
                              Model model) {

        customerService.saveCustomer(customer);
        customerService.saveVehicle(vehicle);

        model.addAttribute("confirmationMsg", "Success");
        model.addAttribute("orderNumber", UUID.randomUUID().toString().substring(0, 8));
        model.addAttribute("dateTime", LocalDateTime.now().toString());
        model.addAttribute("msgToReadEmail", "Check your email");
        model.addAttribute("email", customer.getEmail());

        return "confirmation";
    }
}