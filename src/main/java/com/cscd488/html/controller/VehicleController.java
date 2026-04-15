package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("vehicle")
public class VehicleController {

    @ModelAttribute("vehicle")
    public Vehicle vehicle() {
        return new Vehicle();
    }

    @PostMapping("/vehicle/register")
    public String registerVehicle(@ModelAttribute Vehicle vehicle,
                                  @ModelAttribute("customer") Object customer,
                                  Model model) {

        model.addAttribute("vehicle", vehicle);

        return "reviewPage";
    }
}