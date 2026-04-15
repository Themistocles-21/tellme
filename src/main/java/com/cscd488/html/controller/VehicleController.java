package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.CustomerEntity;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@SessionAttributes({"customer", "vehicle"})
public class ReviewController {

    private final CustomerService customerService;

    public ReviewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/review")
    public String confirm(@ModelAttribute("customer") Customer customer,
                          @ModelAttribute("vehicle") Vehicle vehicle,
                          Model model,
                          HttpSession session) {

        CustomerEntity savedCustomer = customerService.saveCustomer(customer);

        vehicle.setCustomer(savedCustomer);
        customerService.saveVehicle(vehicle);

        session.invalidate();

        model.addAttribute("confirmationMsg", "Success");
        model.addAttribute("orderNumber", UUID.randomUUID().toString().substring(0, 8));
        model.addAttribute("dateTime", LocalDateTime.now().toString());
        model.addAttribute("email", customer.getEmail());

        return "confirmation";
    }
}