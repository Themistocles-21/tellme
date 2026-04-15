package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import org.springframework.stereotype.Controller;
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
    public String vehicleRegistration(@ModelAttribute Vehicle vehicleInf,
                                      @RequestParam Long customerId) throws IOException {

        customerService.saveVehicle(vehicleInf, customerId);
        return "confirmation";
    }
}