package com.cscd488.html.services;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateWorkOrderPdf(Customer customer, Vehicle vehicle, String orderNumber) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            document.setMargins(36, 36, 36, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            // Professional fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            String formattedDateTime = LocalDateTime.now().format(formatter);

            // ===== HEADER SECTION =====
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{50, 50});

            // Left side - Company Info
            PdfPCell companyCell = new PdfPCell();
            companyCell.setBorder(Rectangle.NO_BORDER);
            Paragraph companyName = new Paragraph("AUTO REPAIR CENTER", titleFont);
            companyName.setSpacingAfter(5);
            companyCell.addElement(companyName);
            Paragraph tagline = new Paragraph("Professional Automotive Service", FontFactory.getFont(FontFactory.HELVETICA, 10));
            tagline.setSpacingAfter(3);
            companyCell.addElement(tagline);
            Paragraph address = new Paragraph("123 Main Street, Anytown, USA 12345", normalFont);
            companyCell.addElement(address);
            Paragraph phone = new Paragraph("Tel: (555) 123-4567 | Email: service@autorepair.com", normalFont);
            companyCell.addElement(phone);
            headerTable.addCell(companyCell);

            // Right side - Order Info
            PdfPCell orderCell = new PdfPCell();
            orderCell.setBorder(Rectangle.NO_BORDER);
            orderCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPTable orderInfoTable = new PdfPTable(2);
            orderInfoTable.setWidthPercentage(100);
            orderInfoTable.setWidths(new float[]{40, 60});

            addStyledCell(orderInfoTable, "WORK ORDER #:", orderNumber, labelFont, valueFont);
            addStyledCell(orderInfoTable, "Date:", formattedDateTime, labelFont, valueFont);

            orderCell.addElement(orderInfoTable);
            headerTable.addCell(orderCell);

            document.add(headerTable);

            // Divider line
            Paragraph divider = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8));
            divider.setSpacingAfter(5);
            document.add(divider);

            PdfPTable lineTable = new PdfPTable(1);
            PdfPCell lineCell = new PdfPCell();
            lineCell.setBorder(Rectangle.BOTTOM);
            lineCell.setBorderWidthBottom(1f);
            lineCell.setPadding(0);
            lineTable.addCell(lineCell);
            document.add(lineTable);

            // ===== CUSTOMER & VEHICLE SECTION (Side by side) =====
            PdfPTable mainSection = new PdfPTable(2);
            mainSection.setWidthPercentage(100);
            mainSection.setWidths(new float[]{50, 50});
            mainSection.setSpacingBefore(10);
            mainSection.setSpacingAfter(10);

            // LEFT: Customer Information
            PdfPCell customerSection = createStyledSection("CUSTOMER INFORMATION", sectionFont);
            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setWidths(new float[]{35, 65});

            addStyledCell(customerTable, "Name:", customer.getFname() + " " + customer.getLname(), labelFont, valueFont);
            addStyledCell(customerTable, "Email:", customer.getEmail(), labelFont, valueFont);
            addStyledCell(customerTable, "Phone:", customer.getPhone(), labelFont, valueFont);
            addStyledCell(customerTable, "Address:", customer.getAddress(), labelFont, valueFont);

            customerSection.addElement(customerTable);
            mainSection.addCell(customerSection);

            // RIGHT: Vehicle Information
            PdfPCell vehicleSection = createStyledSection("VEHICLE INFORMATION", sectionFont);
            PdfPTable vehicleTable = new PdfPTable(2);
            vehicleTable.setWidthPercentage(100);
            vehicleTable.setWidths(new float[]{35, 65});

            addStyledCell(vehicleTable, "Make:", vehicle.getMake(), labelFont, valueFont);
            addStyledCell(vehicleTable, "Model:", vehicle.getModel(), labelFont, valueFont);
            addStyledCell(vehicleTable, "Year:", vehicle.getYear(), labelFont, valueFont);
            addStyledCell(vehicleTable, "VIN:", vehicle.getVin(), labelFont, valueFont);

            vehicleSection.addElement(vehicleTable);
            mainSection.addCell(vehicleSection);

            document.add(mainSection);

            // ===== ISSUE DIAGNOSIS SECTION =====
            PdfPCell issueSection = createStyledSection("ISSUE DIAGNOSIS", sectionFont);
            PdfPTable issueTable = new PdfPTable(2);
            issueTable.setWidthPercentage(100);
            issueTable.setWidths(new float[]{30, 70});

            addStyledCell(issueTable, "Location:", formatIssueLocation(vehicle.getIssueLocation()), labelFont, valueFont);
            addStyledCell(issueTable, "Issue Type:", formatIssueType(vehicle.getIssueType()), labelFont, valueFont);
            addStyledCell(issueTable, "Severity:", formatSeverity(vehicle.getSeverity()), labelFont, valueFont);

            issueSection.addElement(issueTable);

            // Customer comments
            if (vehicle.getFreeFormText() != null && !vehicle.getFreeFormText().trim().isEmpty()) {
                Paragraph commentsTitle = new Paragraph("CUSTOMER COMMENTS:", labelFont);
                commentsTitle.setSpacingBefore(10);
                commentsTitle.setSpacingAfter(5);
                issueSection.addElement(commentsTitle);
                Paragraph comments = new Paragraph(vehicle.getFreeFormText(), valueFont);
                comments.setSpacingAfter(10);
                issueSection.addElement(comments);
            }

            // Translated comments
            if (vehicle.getTranslatedText() != null && !vehicle.getTranslatedText().trim().isEmpty()) {
                Paragraph translatedTitle = new Paragraph("TRANSLATED COMMENTS:", labelFont);
                translatedTitle.setSpacingBefore(5);
                translatedTitle.setSpacingAfter(5);
                issueSection.addElement(translatedTitle);
                Paragraph translated = new Paragraph(vehicle.getTranslatedText(), valueFont);
                issueSection.addElement(translated);
            }

            document.add(issueSection);

            // ===== SERVICE NOTES SECTION (For technician use) =====
            PdfPCell notesSection = createStyledSection("SERVICE NOTES", sectionFont);
            Paragraph notesPlaceholder = new Paragraph("___________________________________________________________________", normalFont);
            notesPlaceholder.setSpacingAfter(5);
            notesSection.addElement(notesPlaceholder);
            notesPlaceholder = new Paragraph("___________________________________________________________________", normalFont);
            notesPlaceholder.setSpacingAfter(5);
            notesSection.addElement(notesPlaceholder);
            notesPlaceholder = new Paragraph("___________________________________________________________________", normalFont);
            notesSection.addElement(notesPlaceholder);
            document.add(notesSection);

            // ===== COST ESTIMATE SECTION =====
            PdfPCell costSection = createStyledSection("COST ESTIMATE", sectionFont);
            PdfPTable costTable = new PdfPTable(4);
            costTable.setWidthPercentage(100);
            costTable.setWidths(new float[]{25, 25, 25, 25});

            String[] costHeaders = {"Description", "Qty", "Unit Price", "Total"};
            for (String header : costHeaders) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setPadding(6);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                costTable.addCell(headerCell);
            }

            for (int i = 0; i < 4; i++) {
                PdfPCell emptyCell = new PdfPCell(new Phrase(" ", normalFont));
                emptyCell.setPadding(8);
                emptyCell.setBorder(Rectangle.BOX);
                costTable.addCell(emptyCell);
            }

            costSection.addElement(costTable);
            document.add(costSection);

            // ===== AUTHORIZATION & SIGNATURE =====
            PdfPCell authSection = createStyledSection("AUTHORIZATION", sectionFont);
            Paragraph authText = new Paragraph("I hereby authorize the above repair work and the addition of necessary materials. " +
                    "You are authorized to operate the vehicle for testing and inspection. " +
                    "A mechanic's lien is acknowledged on the vehicle for the amount of repairs. " +
                    "The shop is not responsible for fire, theft, or damage to the vehicle while on premises.", normalFont);
            authText.setSpacingAfter(15);
            authSection.addElement(authText);

            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setWidths(new float[]{50, 50});

            PdfPCell customerSig = new PdfPCell();
            customerSig.setBorder(Rectangle.NO_BORDER);
            customerSig.addElement(new Paragraph("Customer Signature: _____________________________", normalFont));
            customerSig.addElement(new Paragraph("Date: _____________", normalFont));
            signatureTable.addCell(customerSig);

            PdfPCell technicianSig = new PdfPCell();
            technicianSig.setBorder(Rectangle.NO_BORDER);
            technicianSig.addElement(new Paragraph("Technician Signature: _____________________________", normalFont));
            technicianSig.addElement(new Paragraph("Date: _____________", normalFont));
            signatureTable.addCell(technicianSig);

            authSection.addElement(signatureTable);
            document.add(authSection);

            // Footer
            Paragraph footer = new Paragraph("Thank you for choosing Auto Repair Center", FontFactory.getFont(FontFactory.HELVETICA, 8));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF", e);
        }

        return out.toByteArray();
    }

    private PdfPCell createStyledSection(String title, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(12);
        cell.setPaddingBottom(15);

        Paragraph titlePara = new Paragraph(title, font);
        titlePara.setSpacingAfter(12);
        titlePara.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(titlePara);

        return cell;
    }

    private void addStyledCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(4);
        labelCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", valueFont));
        valueCell.setPadding(4);
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private String formatIssueLocation(String location) {
        if (location == null) return "";
        switch(location) {
            case "engine": return "Engine";
            case "transmission": return "Transmission";
            case "brakes": return "Brakes";
            case "electrical": return "Electrical System";
            case "suspension": return "Suspension/Steering";
            case "exhaust": return "Exhaust System";
            case "hvac": return "Heating/AC";
            case "exterior": return "Body/Exterior";
            case "interior": return "Interior";
            default: return location.substring(0, 1).toUpperCase() + location.substring(1);
        }
    }

    private String formatIssueType(String type) {
        if (type == null) return "";
        String formatted = type.replace("_", " ").replace("-", " ");
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }

    private String formatSeverity(String severity) {
        if (severity == null) return "";
        switch(severity) {
            case "mild": return "Mild";
            case "moderate": return "Moderate";
            case "severe": return "Severe";
            case "emergency": return "Emergency";
            default: return severity.substring(0, 1).toUpperCase() + severity.substring(1);
        }
    }
}