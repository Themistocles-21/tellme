package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehicleController {

    @PostMapping("/vehicle/register")
    public String vehicleRegistration(@ModelAttribute Vehicle vehicle,
                                      @ModelAttribute("firstname") String firstname,
                                      @ModelAttribute("lastname") String lastname,
                                      @ModelAttribute("email") String email,
                                      @ModelAttribute("phone") String phone,
                                      @ModelAttribute("address") String address,
                                      Model model) {

        model.addAttribute("customerFirst", firstname);
        model.addAttribute("customerLast", lastname);
        model.addAttribute("customerEmail", email);
        model.addAttribute("customerPhone", phone);
        model.addAttribute("customerAddress", address);

        model.addAttribute("vehicle", vehicle);

        return "reviewPage";
    }
}