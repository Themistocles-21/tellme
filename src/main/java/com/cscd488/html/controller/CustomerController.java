package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("customer")
public class CustomerController {

    @ModelAttribute("customer")
    public Customer customer() {
        return new Customer();
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerInfo";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("customer") Customer customer) {
        return "redirect:/vehicle";
    }
}