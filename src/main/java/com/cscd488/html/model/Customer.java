package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@Controller
public class CustomerController {

    @GetMapping("/home")
    public String home() {
        return "customerInfo";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer,
                                   @RequestParam String language,
                                   Model model) {
        customer.setLanguage(language);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("locale", getLocaleFromCustomer(customer));
        return "vehicleInfo";
    }

    private Locale getLocaleFromCustomer(Customer customer) {
        if (customer == null || customer.getLanguage() == null) {
            return Locale.ENGLISH;
        }
        switch(customer.getLanguage()) {
            case "fa": return new Locale("fa");
            case "ru": return new Locale("ru");
            default: return Locale.ENGLISH;
        }
    }
}