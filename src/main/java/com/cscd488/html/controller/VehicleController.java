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
        return "issueLocation";
    }

    @PostMapping("/vehicle/issue/location")
    public String saveIssueLocation(@ModelAttribute Customer customer,
                                    @ModelAttribute Vehicle vehicle,
                                    @RequestParam String issueLocation,
                                    Model model) {
        vehicle.setIssueLocation(issueLocation);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);
        return "issueType";
    }

    @PostMapping("/vehicle/issue/type")
    public String saveIssueType(@ModelAttribute Customer customer,
                                @ModelAttribute Vehicle vehicle,
                                @RequestParam String issueType,
                                Model model) {
        vehicle.setIssueType(issueType);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);
        return "issueSeverity";
    }

    @PostMapping("/vehicle/issue/severity")
    public String saveIssueSeverity(@ModelAttribute Customer customer,
                                    @ModelAttribute Vehicle vehicle,
                                    @RequestParam String severity,
                                    Model model) {
        vehicle.setSeverity(severity);
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);
        return "issueDetails";
    }

    @PostMapping("/vehicle/issue/details")
    public String saveIssueDetails(@ModelAttribute Customer customer,
                                   @ModelAttribute Vehicle vehicle,
                                   @RequestParam(required = false) String additionalInfo,
                                   Model model) {
        if (additionalInfo != null && !additionalInfo.trim().isEmpty()) {
            vehicle.setFreeFormText(additionalInfo);
        }
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);
        return "reviewPage";
    }
}