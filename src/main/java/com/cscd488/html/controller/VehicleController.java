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

    @PostMapping("/vehicle/register")
    public String vehicleRegistration(@ModelAttribute Vehicle vehicle, Model model) throws Exception {

        customerService.saveVehicle(vehicle);

        model.addAttribute("firstname", model.getAttribute("firstname"));
        model.addAttribute("lastname", model.getAttribute("lastname"));
        model.addAttribute("email", model.getAttribute("email"));
        model.addAttribute("phone", model.getAttribute("phone"));
        model.addAttribute("address", model.getAttribute("address"));

        model.addAttribute("make", vehicle.getMake());
        model.addAttribute("model", vehicle.getModel());
        model.addAttribute("year", vehicle.getYear());
        model.addAttribute("vin", vehicle.getVin());
        model.addAttribute("freeFormText", vehicle.getFreeFormText());

        return "reviewPage";
    }
}