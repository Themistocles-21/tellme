package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.ServiceToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private ServiceToggleService serviceToggleService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        return "customerInfo";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer,
                                   @RequestParam String language,
                                   Model model) {
        customer.setLanguage(language);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        return "vehicleInfo";
    }
}