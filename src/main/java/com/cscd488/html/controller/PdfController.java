package com.cscd488.html.controller;

import com.cscd488.html.model.Vehicle;
import com.cscd488.html.services.CustomerService;
import com.cscd488.html.services.PdfStorageService;
import com.cscd488.html.services.ServiceToggleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class PdfController {

    private final PdfStorageService pdfStorageService;
    private final ServiceToggleService serviceToggleService;
    private final CustomerService customerService;

    public PdfController(PdfStorageService pdfStorageService,
                         ServiceToggleService serviceToggleService,
                         CustomerService customerService) {
        this.pdfStorageService = pdfStorageService;
        this.serviceToggleService = serviceToggleService;
        this.customerService = customerService;
    }

    @GetMapping("/service-writer/pdfs")
    public String viewPdfs(HttpSession session, Model model) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }
        model.addAttribute("pdfs", pdfStorageService.getAllPdfs());
        model.addAttribute("vehicles", customerService.getAllVehicles());
        model.addAttribute("disabledServices", serviceToggleService.getDisabledServices());
        model.addAttribute("disabledLanguages", serviceToggleService.getDisabledServices());
        return "pdfList";
    }

    @GetMapping("/service-writer/pdf/download/{filename}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String filename,
                                                HttpSession session) throws IOException {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return ResponseEntity.status(401).build();
        }

        Resource resource = pdfStorageService.loadPdfAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/service-writer/pdf/view/{filename}")
    public ResponseEntity<byte[]> viewPdf(@PathVariable String filename,
                                          HttpSession session) throws IOException {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return ResponseEntity.status(401).build();
        }

        byte[] pdfBytes = pdfStorageService.loadPdfBytes(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(pdfBytes);
    }

    @PostMapping("/service-writer/pdf/delete/{filename}")
    public String deletePdf(@PathVariable String filename, HttpSession session) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }

        try {
            pdfStorageService.deletePdf(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/service-writer/pdfs";
    }

    @PostMapping("/service-writer/order/update/{id}")
    public String updateOrder(@PathVariable Long id,
                              @RequestParam String status,
                              @RequestParam(required = false) String writerNotes,
                              HttpSession session) {
        if (session.getAttribute("serviceWriterLoggedIn") == null) {
            return "redirect:/service-writer";
        }

        Vehicle existingVehicle = customerService.getVehicleById(id);
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

        return "redirect:/service-writer/pdfs";
    }
}