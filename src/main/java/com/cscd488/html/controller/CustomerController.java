package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.FileWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class CustomerController {

    String welcomeMsg = "Welcome to CSCD488 Project\n\nBelow is customer information\n\n";
    String teamMembers = "Aaron Oehler, Derik Little, Danish Wahidi";

    @GetMapping("home")
    public String index() {
        return "customerInfo";
    }

    @PostMapping("/register")
    public String customerRegistration(@ModelAttribute Customer customerInf, Model model) throws IOException {

        // Add customer info to model so vehicleInfo can receive it
        model.addAttribute("firstname", customerInf.getFname());
        model.addAttribute("lastname", customerInf.getLname());
        model.addAttribute("email", customerInf.getEmail());
        model.addAttribute("phone", customerInf.getPhone());
        model.addAttribute("address", customerInf.getAddress());
        model.addAttribute("teamMembers",teamMembers);

        // Write to file
        String text = welcomeMsg
                + "First Name: " + customerInf.getFname()
                + "\nLast Name: " + customerInf.getLname()
                + "\nEmail: " + customerInf.getEmail()
                + "\nPhone: " + customerInf.getPhone()
                + "\nAddress: " + customerInf.getAddress();

        return "vehicleInfo";
    }
}