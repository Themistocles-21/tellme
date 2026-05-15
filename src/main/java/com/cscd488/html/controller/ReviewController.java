package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import com.cscd488.html.services.EmailSenderService;
import com.cscd488.html.services.FileService;
import com.cscd488.html.services.TranslationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class ReviewController {

    private final CustomerService customerService;
    private final EmailSenderService emailService;

    @Autowired
    private TranslationService translate;

    @Autowired
    private FileService fileWriter;

    public ReviewController(CustomerService customerService, EmailSenderService emailService) {
        this.customerService = customerService;
        this.emailService = emailService;
    }

    @PostMapping("/confirmationPage")
    public String confirm(@ModelAttribute Customer customer,
                          @ModelAttribute Vehicle vehicle,
                          Model model) throws IOException, MessagingException, InterruptedException {

        vehicle.setCustomer(customer);

        Customer existingCustomer = customerService.findByEmail(customer.getEmail());

        if (existingCustomer != null) {
            vehicle.setCustomer(existingCustomer);
            customerService.saveVehicle(vehicle);
        } else {
            customerService.saveCustomer(customer);
            customerService.saveVehicle(vehicle);
        }

        String orderNumber = UUID.randomUUID().toString().substring(0, 8);
        String issueSummary = buildIssueSummary(vehicle);

        try {
            String emailBody = String.format(
                    "Dear %s,\n\nYour service request has been submitted.\n\nOrder Number: %s\nIssue: %s\nSeverity: %s\n\nWe will contact you within 24 hours.\n\nThank you!",
                    customer.getFname(), orderNumber, issueSummary, vehicle.getSeverity()
            );
            emailService.sendSimpleEmail(customer.getEmail(), emailBody, "Service Request Confirmation");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        String originalText = vehicle.getFreeFormText();
        if (originalText != null && !originalText.trim().isEmpty()) {
            String translatedText = translate.translate(originalText);

            String text = formattedDateTime + "\nOrder Number: " + orderNumber + customer.toString() + vehicle.toString();
            String fileName = customer.getLname() + ".txt";
            fileWriter.writeToFile(text, fileName);

            String translationContent = fileWriter.readFromFile("translation.txt");
            fileWriter.append("\n" + translationContent, fileName);

            emailService.sendEmailWithAttachment(
                    customer.getEmail(),
                    text,
                    customer.getLname() + " " + vehicle.getModel() + " issue",
                    fileName
            );
        }

        Customer displayCustomer = (existingCustomer != null) ? existingCustomer : customer;
        model.addAttribute("customer", displayCustomer);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("confirmationMsg", "Your order was successfully submitted!");
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("dateTime", LocalDateTime.now().toString());
        model.addAttribute("email", displayCustomer.getEmail());
        model.addAttribute("msgToReadEmail", "Please check your inbox and make sure email is not marked as spam.");
        model.addAttribute("issueSummary", issueSummary);

        return "confirmationPage";
    }

    private String buildIssueSummary(Vehicle vehicle) {
        return String.format("Location: %s, Issue: %s, Severity: %s",
                vehicle.getIssueLocation(),
                vehicle.getIssueType(),
                vehicle.getSeverity()
        );
    }
}