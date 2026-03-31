package com.cscd488.html.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;



@Controller
public class ConfirmationController {

    String email = "cscd488team@gmail.com";
    String confirmationMsg = "Your order was successfully submitted. We will contact you shortly";
    String msgToReadEmail = "Please check your inbox and make sure below email is not marked as spam or blocked.";

    @PostMapping("/confirmationPage")
    public String finalConfirmation(Model model) {

        // Generate short order number
        String orderNumber = UUID.randomUUID().toString().substring(0, 8);
        model.addAttribute("orderNumber", orderNumber);

        // Format date/time nicely
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("dateTime", formattedDateTime);
        model.addAttribute("msgToReadEmail",msgToReadEmail);
        model.addAttribute("email",email);
        model.addAttribute("confirmationMsg",confirmationMsg);

        System.out.println("File Saved ... \nEmail sent ...");
        return "confirmationPage";
    }
}