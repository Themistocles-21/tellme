package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehicleController {

    @PostMapping("/vehicle/register")
    public String vehicleRegister(@ModelAttribute Customer customer,
                                  @ModelAttribute Vehicle vehicle,
                                  Model model) {

        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);

        return "reviewPage";
    }
}