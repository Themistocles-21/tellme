package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"customer", "vehicle"})
public class VehicleController {

    @ModelAttribute("vehicle")
    public Vehicle vehicle() {
        return new Vehicle();
    }

    @GetMapping("/vehicle")
    public String vehiclePage(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicleInfo";
    }

    @PostMapping("/vehicle/register")
    public String vehicleSubmit(@ModelAttribute("vehicle") Vehicle vehicle,
                                Model model) {

        model.addAttribute("vehicle", vehicle);

        return "reviewPage";
    }
}