package com.cscd488.html.services;

import com.cscd488.html.model.Customer;
import com.cscd488.html.model.Vehicle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("SERVICE WORK ORDER", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Add order info line
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            document.add(new Paragraph("Order #: " + orderNumber + "     Date: " + formattedDateTime));
            document.add(new Paragraph(" "));

            // ===== CUSTOMER INFORMATION SECTION =====
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph customerSection = new Paragraph("CUSTOMER INFORMATION", sectionFont);
            customerSection.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(customerSection);
            document.add(new Paragraph(" "));

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(5);
            customerTable.setSpacingAfter(10);

            addTableRow(customerTable, "Company Name:", "");
            addTableRow(customerTable, "Driver's Name:", customer.getFname() + " " + customer.getLname());
            addTableRow(customerTable, "Driver's Number:", customer.getPhone());
            addTableRow(customerTable, "Email:", customer.getEmail());
            addTableRow(customerTable, "Address:", customer.getAddress());
            addTableRow(customerTable, "Reference Number:", orderNumber);

            document.add(customerTable);
            document.add(new Paragraph(" "));

            // ===== VEHICLE INFORMATION SECTION =====
            Paragraph vehicleSection = new Paragraph("VEHICLE INFORMATION", sectionFont);
            document.add(vehicleSection);
            document.add(new Paragraph(" "));

            PdfPTable vehicleTable = new PdfPTable(2);
            vehicleTable.setWidthPercentage(100);

            addTableRow(vehicleTable, "Make:", vehicle.getMake());
            addTableRow(vehicleTable, "Model:", vehicle.getModel());
            addTableRow(vehicleTable, "Year:", vehicle.getYear());
            addTableRow(vehicleTable, "VIN:", vehicle.getVin());
            addTableRow(vehicleTable, "Unit Type:", "");
            addTableRow(vehicleTable, "Manufacturer:", "");

            document.add(vehicleTable);
            document.add(new Paragraph(" "));

            // ===== COMPLAINT / ISSUE SECTION =====
            Paragraph complaintSection = new Paragraph("COMPLAINT / ISSUE DIAGNOSIS", sectionFont);
            document.add(complaintSection);
            document.add(new Paragraph(" "));

            PdfPTable issueTable = new PdfPTable(2);
            issueTable.setWidthPercentage(100);

            addTableRow(issueTable, "Problem Area:", vehicle.getIssueLocation());
            addTableRow(issueTable, "Issue Type:", vehicle.getIssueType());
            addTableRow(issueTable, "Severity:", vehicle.getSeverity());

            document.add(issueTable);

            // Additional comments
            if (vehicle.getFreeFormText() != null && !vehicle.getFreeFormText().trim().isEmpty()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Customer Comments:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                document.add(new Paragraph(vehicle.getFreeFormText()));
            }

            // Translated comments
            if (vehicle.getTranslatedText() != null && !vehicle.getTranslatedText().trim().isEmpty()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Translated Comments:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                document.add(new Paragraph(vehicle.getTranslatedText()));
            }

            document.add(new Paragraph(" "));

            // ===== AUTHORIZATION STATEMENT =====
            Paragraph authSection = new Paragraph("AUTHORIZATION", sectionFont);
            document.add(authSection);
            document.add(new Paragraph(" "));

            String authText = "I hereby authorize the repair work to be done along with the necessary materials. " +
                    "You and your employees may operate vehicle for purposes of testing, inspection or delivery at my risk. " +
                    "An express mechanic's lien is acknowledged on vehicle to secure the amount of repairs thereto. " +
                    "You will not be held responsible for loss or damage to vehicle or articles left in vehicle in case " +
                    "of fire, theft, accident or any other cause beyond your control.";

            Paragraph authParagraph = new Paragraph(authText, FontFactory.getFont(FontFactory.HELVETICA, 10));
            authParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(authParagraph);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // ===== SIGNATURE LINE =====
            Paragraph signatureLine = new Paragraph("Customer Signature: _______________________________     Date: _____________");
            document.add(signatureLine);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF", e);
        }

        return out.toByteArray();
    }

    private void addTableRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(4);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", FontFactory.getFont(FontFactory.HELVETICA)));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(4);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}