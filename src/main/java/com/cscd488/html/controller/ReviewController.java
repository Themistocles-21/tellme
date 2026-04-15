package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

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

        customerService.saveCustomer(customer);
        customerService.saveVehicle(vehicle);

        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);

        model.addAttribute("confirmationMsg", "Submission complete!");
        model.addAttribute("orderNumber", vehicle.getVin());
        model.addAttribute("dateTime", LocalDateTime.now().toString());

        return "confirmationPage";
    }
}