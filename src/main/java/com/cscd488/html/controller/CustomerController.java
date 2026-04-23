package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
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

        // Store language in customer object
        customer.setLanguage(language);

        // Set locale for internationalization
        Locale locale;
        switch(language) {
            case "fa":
                locale = new Locale("fa");
                break;
            case "ru":
                locale = new Locale("ru");
                break;
            default:
                locale = Locale.ENGLISH;
        }

        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", new com.cscd488.html.model.Vehicle());
        model.addAttribute("locale", locale);

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