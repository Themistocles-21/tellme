package com.cscd488.html.controller;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import com.cscd488.html.services.EmailSenderService;
import com.cscd488.html.services.FileService;
import com.cscd488.html.services.TranslationService;
import com.cscd488.html.services.ServiceWriterConfigService;
import com.cscd488.html.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class ReviewController {

    private final CustomerService customerService;
    private final EmailSenderService emailService;
    private final ServiceWriterConfigService serviceWriterConfigService;
    private final PdfService pdfService;

    @Autowired
    private TranslationService translate;

    public ReviewController(CustomerService customerService,
                            EmailSenderService emailService,
                            ServiceWriterConfigService serviceWriterConfigService,
                            PdfService pdfService) {
        this.customerService = customerService;
        this.emailService = emailService;
        this.serviceWriterConfigService = serviceWriterConfigService;
        this.pdfService = pdfService;
    }

    @PostMapping("/confirmationPage")
    public String confirm(@ModelAttribute Customer customer,
                          @ModelAttribute Vehicle vehicle,
                          Model model) {
        try {
            vehicle.setCustomer(customer);

            // Check if customer already exists in database
            Customer existingCustomer = customerService.findByEmail(customer.getEmail());
            Customer savedCustomer;
            Vehicle savedVehicle;

            if (existingCustomer != null) {
                vehicle.setCustomer(existingCustomer);
                customerService.saveVehicle(vehicle);
                savedCustomer = existingCustomer;
                savedVehicle = vehicle;
            } else {
                customerService.saveCustomer(customer);
                customerService.saveVehicle(vehicle);
                savedCustomer = customer;
                savedVehicle = vehicle;
            }

            String orderNumber = UUID.randomUUID().toString().substring(0, 8);
            String issueSummary = buildIssueSummary(savedVehicle);

            // Send simple confirmation email to customer
            try {
                String emailBody = String.format(
                        "Dear %s,\n\nYour service request has been submitted.\n\nOrder Number: %s\nIssue: %s\nSeverity: %s\n\nWe will contact you within 24 hours.\n\nThank you!",
                        savedCustomer.getFname(), orderNumber, issueSummary, savedVehicle.getSeverity()
                );
                emailService.sendSimpleEmail(savedCustomer.getEmail(), emailBody, "Service Request Confirmation");
            } catch (Exception e) {
                System.err.println("Failed to send email to customer: " + e.getMessage());
            }

            String originalText = savedVehicle.getFreeFormText();
            String translatedText = "";

            if (originalText != null && !originalText.trim().isEmpty()) {
                try {
                    translatedText = translate.translate(originalText);
                    savedVehicle.setTranslatedText(translatedText);
                    customerService.saveVehicle(savedVehicle); // Update with translation

                    // Generate PDF work order using saved database objects
                    byte[] pdfBytes = pdfService.generateWorkOrderPdf(savedCustomer, savedVehicle, orderNumber);
                    String pdfFileName = savedCustomer.getLname() + "_" + orderNumber + ".pdf";

                    // Send email with PDF attachment to Service Writer
                    String serviceWriterEmail = serviceWriterConfigService.getServiceWriterEmail();
                    emailService.sendEmailWithPdfAttachment(
                            serviceWriterEmail,
                            "New service request submitted. Work order attached.",
                            "Work Order - " + orderNumber + " - " + savedCustomer.getLname() + " " + savedVehicle.getModel(),
                            pdfBytes,
                            pdfFileName
                    );

                    System.out.println("PDF generated from database records and sent to service writer: " + serviceWriterEmail);
                    System.out.println("Order Number: " + orderNumber);

                } catch (Exception e) {
                    System.err.println("PDF generation or email failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            model.addAttribute("customer", savedCustomer);
            model.addAttribute("vehicle", savedVehicle);
            model.addAttribute("confirmationMsg", "Your order was successfully submitted!");
            model.addAttribute("orderNumber", orderNumber);
            model.addAttribute("dateTime", LocalDateTime.now().toString());
            model.addAttribute("email", savedCustomer.getEmail());
            model.addAttribute("msgToReadEmail", "Please check your inbox and make sure email is not marked as spam.");
            model.addAttribute("issueSummary", issueSummary);
            model.addAttribute("originalText", originalText);
            model.addAttribute("translatedText", translatedText);

            return "confirmationPage";

        } catch (Exception e) {
            System.err.println("Error in confirmation: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "An error occurred processing your request: " + e.getMessage());
            return "customerInfo";
        }
    }

    private String buildIssueSummary(Vehicle vehicle) {
        return String.format("Location: %s, Issue: %s, Severity: %s",
                vehicle.getIssueLocation(),
                vehicle.getIssueType(),
                vehicle.getSeverity()
        );
    }
}