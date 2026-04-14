package com.cscd488.html.controller;

import com.cscd488.html.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.cscd488.html.model.Customer;
import com.cscd488.html.model.CustomerFileWriter;
import com.cscd488.html.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.mail.MessagingException;  // ← CHANGE THIS
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class ReviewController {

    @GetMapping("confirmation")
    public String confirmation(){
        return "confirmationPage";
    }

    CustomerFileWriter customerFileWriter = new CustomerFileWriter();
    @Autowired
    private EmailSenderService service;

    @PostMapping("/review")
    public String reviewPage(
            @ModelAttribute Customer customer,
            @ModelAttribute Vehicle vehicle,
            Model model) throws IOException, MessagingException {


        // Customer fields
        model.addAttribute("firstname", customer.getFname());
        model.addAttribute("lastname", customer.getLname());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("phone", customer.getPhone());
        model.addAttribute("address", customer.getAddress());

        // Vehicle fields
        model.addAttribute("make", vehicle.getMake());
        model.addAttribute("model", vehicle.getModel());
        model.addAttribute("year", vehicle.getYear());
        model.addAttribute("vin", vehicle.getVin());
        model.addAttribute("freeFormText", vehicle.getFreeFormText());

        // Format date/time nicely
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        String orderNumber = UUID.randomUUID().toString().substring(0, 8);

        //If file writer is here the order number will be different. But it works here !
        String text = formattedDateTime + "\nOrder Number: " + orderNumber + customer.toString() + vehicle.toString();

        customerFileWriter.writeToFile(text, customer.getLname() + ".txt");

        //sends email without attachment
        // service.sendSimpleEmail(customer.getEmail(), text,customer.getLname());

        // sends email with attachment
        service.sendEmailWithAttachment(customer.getEmail(),text, customer.getLname() + " " + vehicle.getModel() + " issue", customer.getLname() + ".txt");


        return "reviewPage";
    }
}