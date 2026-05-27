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

            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            String formattedDateTime = LocalDateTime.now().format(formatter);

            // ===== HEADER SECTION (Order # and Date) =====
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{50, 50});

            PdfPCell leftHeader = new PdfPCell(new Phrase(" ", normalFont));
            leftHeader.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(leftHeader);

            PdfPCell rightHeader = new PdfPCell(new Phrase("Order #: " + orderNumber + "     Date: " + formattedDateTime, normalFont));
            rightHeader.setBorder(Rectangle.NO_BORDER);
            rightHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(rightHeader);
            document.add(headerTable);

            // ===== CUSTOMER INFORMATION & TECHNICIAN INFO (side by side) =====
            PdfPTable topSection = new PdfPTable(2);
            topSection.setWidthPercentage(100);
            topSection.setWidths(new float[]{50, 50});

            // LEFT: CUSTOMER INFORMATION
            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(Rectangle.BOX);
            customerCell.setPadding(3);

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setWidths(new float[]{35, 65});

            addCell(customerTable, "Company Name:", getValueOrBlank(customer.getCompanyName()), boldFont, normalFont);
            addCell(customerTable, "Driver's Name:", customer.getFname() + " " + customer.getLname(), boldFont, normalFont);
            addCell(customerTable, "Driver's Number:", customer.getPhone(), boldFont, normalFont);
            addCell(customerTable, "Email:", customer.getEmail(), boldFont, normalFont);
            addCell(customerTable, "Address:", customer.getAddress(), boldFont, normalFont);
            addCell(customerTable, "Reference Number:", orderNumber, boldFont, normalFont);
            addCell(customerTable, "Purchase Order:", "", boldFont, normalFont);

            customerCell.addElement(new Paragraph("CUSTOMER INFORMATION", titleFont));
            customerCell.addElement(new Paragraph(" "));
            customerCell.addElement(customerTable);
            topSection.addCell(customerCell);

            // RIGHT: TECHNICIAN INFO (fill-in blanks)
            PdfPCell techCell = new PdfPCell();
            techCell.setBorder(Rectangle.BOX);
            techCell.setPadding(3);

            PdfPTable techTable = new PdfPTable(2);
            techTable.setWidthPercentage(100);
            techTable.setWidths(new float[]{35, 65});

            addCell(techTable, "Employee Name:", "_________________", boldFont, normalFont);
            addCell(techTable, "Employee #:", "_________________", boldFont, normalFont);
            addCell(techTable, "Authorization Person:", "_________________", boldFont, normalFont);
            addCell(techTable, "Vehicle #:", "_________________", boldFont, normalFont);
            addCell(techTable, "Travel Mileage:", "_________________", boldFont, normalFont);
            addCell(techTable, "Time on Job:", "_________________", boldFont, normalFont);
            addCell(techTable, "Start:", "_________", boldFont, normalFont);
            addCell(techTable, "End:", "_________", boldFont, normalFont);

            techCell.addElement(new Paragraph("TECHNICIAN INFO", titleFont));
            techCell.addElement(new Paragraph(" "));
            techCell.addElement(techTable);
            topSection.addCell(techCell);

            document.add(topSection);

            // ===== VEHICLE INFORMATION & UNIT INFORMATION (side by side) =====
            PdfPTable middleSection = new PdfPTable(2);
            middleSection.setWidthPercentage(100);
            middleSection.setWidths(new float[]{50, 50});

            // LEFT: VEHICLE INFORMATION
            PdfPCell vehicleCell = new PdfPCell();
            vehicleCell.setBorder(Rectangle.BOX);
            vehicleCell.setPadding(3);

            PdfPTable vehicleTable = new PdfPTable(2);
            vehicleTable.setWidthPercentage(100);
            vehicleTable.setWidths(new float[]{35, 65});

            addCell(vehicleTable, "Manufacture:", vehicle.getMake(), boldFont, normalFont);
            addCell(vehicleTable, "Model:", vehicle.getModel(), boldFont, normalFont);
            addCell(vehicleTable, "Year:", vehicle.getYear(), boldFont, normalFont);
            addCell(vehicleTable, "VIN:", vehicle.getVin(), boldFont, normalFont);
            addCell(vehicleTable, "License Plate:", getValueOrBlank(vehicle.getLicensePlate()), boldFont, normalFont);
            addCell(vehicleTable, "Current Mileage:", getValueOrBlank(vehicle.getMileage()), boldFont, normalFont);

            vehicleCell.addElement(new Paragraph("VEHICLE INFORMATION", titleFont));
            vehicleCell.addElement(new Paragraph(" "));
            vehicleCell.addElement(vehicleTable);
            middleSection.addCell(vehicleCell);

            // RIGHT: UNIT INFORMATION (fill-in blanks)
            PdfPCell unitCell = new PdfPCell();
            unitCell.setBorder(Rectangle.BOX);
            unitCell.setPadding(3);

            PdfPTable unitTable = new PdfPTable(2);
            unitTable.setWidthPercentage(100);
            unitTable.setWidths(new float[]{40, 60});

            addCell(unitTable, "Unit Number:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Unit Type:", "_________________", boldFont, normalFont);
            addCell(unitTable, "BM Number:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Serial Number:", "_________________", boldFont, normalFont);
            addCell(unitTable, "COD:", "Yes ___ No ___", boldFont, normalFont);
            addCell(unitTable, "Payment Type:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Total Hours:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Engine Hours:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Electric Hours:", "_________________", boldFont, normalFont);
            addCell(unitTable, "Loaded:", "Yes ___ No ___", boldFont, normalFont);
            addCell(unitTable, "Waiting:", "Yes ___ No ___", boldFont, normalFont);
            addCell(unitTable, "Dropped:", "Yes ___ No ___", boldFont, normalFont);
            addCell(unitTable, "Date Needed:", "_____________", boldFont, normalFont);

            unitCell.addElement(new Paragraph("UNIT INFORMATION", titleFont));
            unitCell.addElement(new Paragraph(" "));
            unitCell.addElement(unitTable);
            middleSection.addCell(unitCell);

            document.add(middleSection);

            // ===== MILEAGE (separate row) =====
            PdfPTable mileageTable = new PdfPTable(2);
            mileageTable.setWidthPercentage(100);
            mileageTable.setWidths(new float[]{15, 85});
            addCell(mileageTable, "MILEAGE:", getValueOrBlank(vehicle.getMileage()), boldFont, normalFont);
            document.add(mileageTable);

            // ===== COMPLAINT SECTION =====
            PdfPTable complaintTable = new PdfPTable(1);
            complaintTable.setWidthPercentage(100);
            PdfPCell complaintCell = new PdfPCell();
            complaintCell.setBorder(Rectangle.BOX);
            complaintCell.setPadding(3);

            StringBuilder complaintText = new StringBuilder();
            complaintText.append("Location: ").append(vehicle.getIssueLocation()).append("  |  ");
            complaintText.append("Issue: ").append(vehicle.getIssueType()).append("  |  ");
            complaintText.append("Severity: ").append(vehicle.getSeverity());

            Paragraph complaintPara = new Paragraph(complaintText.toString(), normalFont);
            complaintCell.addElement(new Paragraph("COMPLAINT:", boldFont));
            complaintCell.addElement(complaintPara);

            if (vehicle.getFreeFormText() != null && !vehicle.getFreeFormText().trim().isEmpty()) {
                complaintCell.addElement(new Paragraph("Customer Comments: " + vehicle.getFreeFormText(), normalFont));
            }
            if (vehicle.getTranslatedText() != null && !vehicle.getTranslatedText().trim().isEmpty()) {
                complaintCell.addElement(new Paragraph("Translated Comments: " + vehicle.getTranslatedText(), normalFont));
            }

            // Add blank lines for technician notes
            complaintCell.addElement(new Paragraph(" "));
            complaintCell.addElement(new Paragraph("Technician Notes: _______________________________________________", normalFont));
            complaintCell.addElement(new Paragraph("_______________________________________________________________", normalFont));

            complaintTable.addCell(complaintCell);
            document.add(complaintTable);

            // ===== AUTHORIZATION SECTION =====
            PdfPTable authTable = new PdfPTable(1);
            authTable.setWidthPercentage(100);
            PdfPCell authCell = new PdfPCell();
            authCell.setBorder(Rectangle.BOX);
            authCell.setPadding(3);

            String authText = "I hereby authorize the repair work to be done along with the necessary materials. " +
                    "You and your employees may operate vehicle for purposes of testing, inspection or delivery at my risk. " +
                    "An express mechanic's lien is acknowledged on vehicle to secure the amount of repairs thereto. " +
                    "You will not be held responsible for loss or damage to vehicle or articles left in vehicle in case of fire, theft, accident or any other cause beyond your control.";

            authCell.addElement(new Paragraph(authText, FontFactory.getFont(FontFactory.HELVETICA, 7)));
            authTable.addCell(authCell);
            document.add(authTable);

            // ===== TOTAL COST SECTION =====
            PdfPTable totalTable = new PdfPTable(6);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{16, 16, 16, 16, 18, 18});

            String[] totalHeaders = {"Parts", "Labor", "Shop Supplies", "Env. Fees", "Subtotal", "Total"};
            for (String header : totalHeaders) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, boldFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(3);
                totalTable.addCell(headerCell);
            }

            for (int i = 0; i < 6; i++) {
                PdfPCell emptyCell = new PdfPCell(new Phrase("", normalFont));
                emptyCell.setPadding(4);
                totalTable.addCell(emptyCell);
            }

            document.add(totalTable);

            // ===== SIGNATURE LINES =====
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setWidths(new float[]{50, 50});

            PdfPCell customerSigCell = new PdfPCell();
            customerSigCell.setBorder(Rectangle.NO_BORDER);
            customerSigCell.addElement(new Paragraph("Customer Signature: _______________________________", normalFont));
            customerSigCell.addElement(new Paragraph("Date: _____________", normalFont));
            signatureTable.addCell(customerSigCell);

            PdfPCell techSigCell = new PdfPCell();
            techSigCell.setBorder(Rectangle.NO_BORDER);
            techSigCell.addElement(new Paragraph("Technician Signature: _______________________________", normalFont));
            techSigCell.addElement(new Paragraph("Date: _____________", normalFont));
            signatureTable.addCell(techSigCell);

            document.add(signatureTable);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF", e);
        }

        return out.toByteArray();
    }

    private void addCell(PdfPTable table, String label, String value, Font boldFont, Font normalFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, boldFont));
        labelCell.setPadding(3);
        labelCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", normalFont));
        valueCell.setPadding(3);
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private String getValueOrBlank(String value) {
        return (value != null && !value.isEmpty()) ? value : "_________________";
    }
}