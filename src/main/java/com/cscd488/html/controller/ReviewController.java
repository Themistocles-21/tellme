package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    private final CustomerService customerService;

    public ReviewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/review")
    public String review(@ModelAttribute Customer customer,
                         @ModelAttribute Vehicle vehicle,
                         Model model) {

        model.addAttribute("firstname", customer.getFname());
        model.addAttribute("lastname", customer.getLname());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("phone", customer.getPhone());
        model.addAttribute("address", customer.getAddress());

        model.addAttribute("make", vehicle.getMake());
        model.addAttribute("model", vehicle.getModel());
        model.addAttribute("year", vehicle.getYear());
        model.addAttribute("vin", vehicle.getVin());
        model.addAttribute("freeFormText", vehicle.getFreeFormText());

        return "reviewPage";
    }

    @PostMapping("/confirmationPage")
    public String confirm(@ModelAttribute Customer customer,
                          @ModelAttribute Vehicle vehicle,
                          Model model) {

        customerService.saveCustomer(customer);
        customerService.saveVehicle(vehicle, customer.getEmail());

        model.addAttribute("confirmationMsg", "Submission complete!");
        model.addAttribute("orderNumber", vehicle.getVin());
        model.addAttribute("dateTime", java.time.LocalDateTime.now().toString());
        model.addAttribute("msgToReadEmail", "Check your email");
        model.addAttribute("email", customer.getEmail());

        return "confirmationPage";
    }
}