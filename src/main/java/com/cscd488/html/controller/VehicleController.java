package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@Controller
public class VehicleController {

    @PostMapping("/vehicle/register")
    public String vehicleRegister(@ModelAttribute Customer customer,
                                  @ModelAttribute Vehicle vehicle,
                                  Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("locale", getLocaleFromCustomer(customer));
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
        model.addAttribute("locale", getLocaleFromCustomer(customer));
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
        model.addAttribute("locale", getLocaleFromCustomer(customer));
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
        model.addAttribute("locale", getLocaleFromCustomer(customer));
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
        model.addAttribute("locale", getLocaleFromCustomer(customer));
        return "reviewPage";
    }

    private Locale getLocaleFromCustomer(Customer customer) {
        if (customer == null || customer.getLanguage() == null) {
            return Locale.ENGLISH;
        }
        switch(customer.getLanguage()) {
            case "fa": return new Locale("fa");
            case "ru": return new Locale("ru");
            default: return Locale.ENGLISH;
        }
    }
}