package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
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
    private final CustomerService customerService;

    public ServiceWriterController(ServiceToggleService serviceToggleService,
                                   ServiceWriterConfigService serviceWriterConfigService,
                                   CustomerService customerService) {
        this.serviceToggleService = serviceToggleService;
        this.serviceWriterConfigService = serviceWriterConfigService;
        this.customerService = customerService;
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

    @GetMapping("/service-writer/orders")
    public String viewAllOrders(HttpSession session, Model model) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        model.addAttribute("vehicles", customerService.getAllVehicles());
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        return "workOrderList";
    }

    @GetMapping("/service-writer/order/{id}")
    public String editOrder(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        Vehicle vehicle = customerService.getVehicleById(id);
        if (vehicle == null) {
            return "redirect:/service-writer/orders";
        }
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        return "editWorkOrder";
    }

    @PostMapping("/service-writer/order/update")
    public String updateOrder(@ModelAttribute Vehicle vehicle,
                              @RequestParam(required = false) String writerNotes,
                              @RequestParam String status,
                              HttpSession session) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        Vehicle existingVehicle = customerService.getVehicleById(vehicle.getId());
        if (existingVehicle != null) {
            existingVehicle.setStatus(status);
            if (writerNotes != null && !writerNotes.trim().isEmpty()) {
                String currentNotes = existingVehicle.getWriterNotes();
                String newNote = writerNotes.trim();
                if (currentNotes == null || currentNotes.isEmpty()) {
                    existingVehicle.setWriterNotes(newNote);
                } else {
                    existingVehicle.setWriterNotes(currentNotes + "\n---\n" + newNote);
                }
            }
            customerService.saveVehicle(existingVehicle);
        }
        return "redirect:/service-writer/orders";
    }

    @PostMapping("/service-writer/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/service-writer";
    }
}