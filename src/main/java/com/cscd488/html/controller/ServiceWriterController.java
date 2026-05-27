package com.cscd488.html.controller;

import com.cscd488.html.services.ServiceToggleService;
import com.cscd488.html.services.ServiceWriterConfigService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServiceWriterController {

    private final ServiceToggleService serviceToggleService;
    private final ServiceWriterConfigService serviceWriterConfigService;

    public ServiceWriterController(ServiceToggleService serviceToggleService,
                                   ServiceWriterConfigService serviceWriterConfigService) {
        this.serviceToggleService = serviceToggleService;
        this.serviceWriterConfigService = serviceWriterConfigService;
    }

    @GetMapping("/service-writer")
    public String showServiceWriterLogin() {
        return "serviceWriterLogin";
    }

    @PostMapping("/service-writer/login")
    public String loginServiceWriter(@RequestParam String username,
                                     @RequestParam String password,
                                     Model model,
                                     HttpSession session) {
        if (username.equals("writer") && password.equals("password")) {
            session.setAttribute("serviceWriterLoggedIn", true);
            return "redirect:/service-writer/dashboard";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "serviceWriterLogin";
    }

    @GetMapping("/service-writer/dashboard")
    public String showServiceWriterDashboard(HttpSession session, Model model) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        model.addAttribute("loggedIn", true);
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        model.addAttribute("disabledLanguages", serviceToggleService.getDisabledServices());
        model.addAttribute("serviceWriterEmail", serviceWriterConfigService.getServiceWriterEmail());
        return "serviceWriterDashboard";
    }

    @PostMapping("/service-writer/toggle")
    public String toggleService(@RequestParam String serviceKey,
                                HttpSession session) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        if (serviceToggleService.isDisabled(serviceKey)) {
            serviceToggleService.enableService(serviceKey);
        } else {
            serviceToggleService.disableService(serviceKey);
        }
        return "redirect:/service-writer/dashboard";
    }

    @PostMapping("/service-writer/update-email")
    public String updateServiceWriterEmail(@RequestParam String serviceWriterEmail,
                                           HttpSession session,
                                           Model model) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        serviceWriterConfigService.setServiceWriterEmail(serviceWriterEmail);
        model.addAttribute("emailUpdated", true);
        model.addAttribute("loggedIn", true);
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        model.addAttribute("disabledLanguages", serviceToggleService.getDisabledServices());
        model.addAttribute("serviceWriterEmail", serviceWriterConfigService.getServiceWriterEmail());
        return "serviceWriterDashboard";
    }

    @PostMapping("/service-writer/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/service-writer";
    }
}