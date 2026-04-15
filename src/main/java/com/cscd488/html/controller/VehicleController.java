package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class VehicleController {

    private final CustomerService customerService;

    public VehicleController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/vehicle")
    public String vehicleInfo() {
        return "vehicleInfo";
    }

    @PostMapping("/vehicle/register")
    public String vehicleRegistration(@ModelAttribute Vehicle vehicle,
                                      @RequestParam String email,
                                      Model model) throws IOException {

        customerService.saveVehicle(vehicle, email);

        model.addAttribute("confirmationMsg", "Vehicle submitted successfully");
        model.addAttribute("orderNumber", vehicle.getVin());
        model.addAttribute("dateTime", java.time.LocalDateTime.now().toString());
        model.addAttribute("msgToReadEmail", "Check your email for details");
        model.addAttribute("email", email);

        return "confirmation";
    }
}