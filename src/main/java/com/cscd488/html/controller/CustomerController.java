package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private static final String TEAM_MEMBERS =
            "Aaron Oehler, Derik Little, Danish Wahidi";

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("home")
    public String index() {
        return "customerInfo";
    }

    @PostMapping("/register")
    public String customerRegistration(@ModelAttribute Customer customerInf, Model model) throws IOException {

        model.addAttribute("firstname", customerInf.getFname());
        model.addAttribute("lastname", customerInf.getLname());
        model.addAttribute("email", customerInf.getEmail());
        model.addAttribute("phone", customerInf.getPhone());
        model.addAttribute("address", customerInf.getAddress());
        model.addAttribute("teamMembers", TEAM_MEMBERS);
        
        customerService.saveCustomer(customerInf);

        return "vehicleInfo";
    }
}