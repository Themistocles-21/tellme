package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @GetMapping("/home")
    public String home() {
        return "customerInfo";
    }

    @PostMapping("/register")
    public String customerRegistration(@ModelAttribute Customer customer, Model model) {

        model.addAttribute("firstname", customer.getFname());
        model.addAttribute("lastname", customer.getLname());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("phone", customer.getPhone());
        model.addAttribute("address", customer.getAddress());

        return "vehicleInfo";
    }
}