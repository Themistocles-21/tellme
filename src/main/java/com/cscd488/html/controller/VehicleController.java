package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehicleController {

    private final CustomerService customerService;

    public VehicleController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/vehicle")
    public String vehiclePage() {
        return "vehicleInfo";
    }

    @PostMapping("/vehicle/register")
    public String vehicleRegister(@ModelAttribute Vehicle vehicle,
                                  Model model) {

        customerService.saveVehicle(vehicle);

        model.addAttribute("confirmationMsg", "Vehicle saved successfully");
        model.addAttribute("orderNumber", vehicle.getVin());
        model.addAttribute("dateTime", java.time.LocalDateTime.now().toString());
        model.addAttribute("msgToReadEmail", "Check email for details");
        model.addAttribute("email", "not linked yet");

        return "confirmationPage";
    }
}