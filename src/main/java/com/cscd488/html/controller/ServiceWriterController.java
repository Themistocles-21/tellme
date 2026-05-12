package com.cscd488.html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServiceWriterController {

    @GetMapping("/service-writer")
    public String showServiceWriterLogin() {
        return "serviceWriterLogin";
    }

    @PostMapping("/service-writer/login")
    public String loginServiceWriter(@RequestParam String username,
                                     @RequestParam String password,
                                     Model model) {

        if (username.equals("writer") && password.equals("password")) {
            return "redirect:/service-writer/dashboard";
        }

        model.addAttribute("error", "Invalid username or password.");
        return "serviceWriterLogin";
    }

    @GetMapping("/service-writer/dashboard")
    public String showServiceWriterDashboard() {
        return "serviceWriterDashboard";
    }
}