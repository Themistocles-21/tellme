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

            // Font definitions
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            String formattedDateTime = LocalDateTime.now().format(formatter);

            // ===== HEADER SECTION =====
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{50, 50});

            // Left side - empty or company logo area
            PdfPCell leftHeader = new PdfPCell(new Phrase(" ", normalFont));
            leftHeader.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(leftHeader);

            // Right side - Order # and Date
            PdfPCell rightHeader = new PdfPCell(new Phrase("Order #: " + orderNumber + "          Date: " + formattedDateTime, normalFont));
            rightHeader.setBorder(Rectangle.NO_BORDER);
            rightHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(rightHeader);

            document.add(headerTable);
            document.add(new Paragraph(" "));

            // ===== CUSTOMER INFORMATION & TECHNICIAN INFO (Top section with two tables side by side) =====
            PdfPTable topSectionTable = new PdfPTable(2);
            topSectionTable.setWidthPercentage(100);
            topSectionTable.setWidths(new float[]{50, 50});

            // Left: CUSTOMER INFORMATION
            PdfPCell customerInfoCell = new PdfPCell();
            customerInfoCell.setBorder(Rectangle.BOX);
            customerInfoCell.setPadding(5);

            PdfPTable customerInfoTable = new PdfPTable(2);
            customerInfoTable.setWidthPercentage(100);
            customerInfoTable.setWidths(new float[]{40, 60});

            addBorderedTableRow(customerInfoTable, "Company Name:", "", boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Driver's Name:", customer.getFname() + " " + customer.getLname(), boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Driver's Number:", customer.getPhone(), boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Email:", customer.getEmail(), boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Address:", customer.getAddress(), boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Reference Number:", orderNumber, boldFont, normalFont);
            addBorderedTableRow(customerInfoTable, "Purchase Order:", "", boldFont, normalFont);

            customerInfoCell.addElement(new Paragraph("CUSTOMER INFORMATION", boldFont));
            customerInfoCell.addElement(new Paragraph(" "));
            customerInfoCell.addElement(customerInfoTable);
            topSectionTable.addCell(customerInfoCell);

            // Right: TECHNICIAN INFO
            PdfPCell techInfoCell = new PdfPCell();
            techInfoCell.setBorder(Rectangle.BOX);
            techInfoCell.setPadding(5);

            PdfPTable techInfoTable = new PdfPTable(2);
            techInfoTable.setWidthPercentage(100);
            techInfoTable.setWidths(new float[]{40, 60});

            addBorderedTableRow(techInfoTable, "Employee Name:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Employee #:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Authorization Person:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Vehicle #:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Travel Mileage:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Time on Job:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "Start:", "", boldFont, normalFont);
            addBorderedTableRow(techInfoTable, "End:", "", boldFont, normalFont);

            techInfoCell.addElement(new Paragraph("TECHNICIAN INFO", boldFont));
            techInfoCell.addElement(new Paragraph(" "));
            techInfoCell.addElement(techInfoTable);
            topSectionTable.addCell(techInfoCell);

            document.add(topSectionTable);
            document.add(new Paragraph(" "));

            // ===== VEHICLE INFORMATION & UNIT INFORMATION (side by side) =====
            PdfPTable middleSectionTable = new PdfPTable(2);
            middleSectionTable.setWidthPercentage(100);
            middleSectionTable.setWidths(new float[]{50, 50});

            // Left: VEHICLE INFORMATION
            PdfPCell vehicleInfoCell = new PdfPCell();
            vehicleInfoCell.setBorder(Rectangle.BOX);
            vehicleInfoCell.setPadding(5);

            PdfPTable vehicleInfoTable = new PdfPTable(2);
            vehicleInfoTable.setWidthPercentage(100);
            vehicleInfoTable.setWidths(new float[]{40, 60});

            addBorderedTableRow(vehicleInfoTable, "Make:", vehicle.getMake(), boldFont, normalFont);
            addBorderedTableRow(vehicleInfoTable, "Model:", vehicle.getModel(), boldFont, normalFont);
            addBorderedTableRow(vehicleInfoTable, "Year:", vehicle.getYear(), boldFont, normalFont);
            addBorderedTableRow(vehicleInfoTable, "VIN:", vehicle.getVin(), boldFont, normalFont);

            vehicleInfoCell.addElement(new Paragraph("VEHICLE INFORMATION", boldFont));
            vehicleInfoCell.addElement(new Paragraph(" "));
            vehicleInfoCell.addElement(vehicleInfoTable);
            middleSectionTable.addCell(vehicleInfoCell);

            // Right: UNIT INFORMATION
            PdfPCell unitInfoCell = new PdfPCell();
            unitInfoCell.setBorder(Rectangle.BOX);
            unitInfoCell.setPadding(5);

            PdfPTable unitInfoTable = new PdfPTable(2);
            unitInfoTable.setWidthPercentage(100);
            unitInfoTable.setWidths(new float[]{40, 60});

            addBorderedTableRow(unitInfoTable, "Unit Number:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Unit Type:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "BM Number:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Serial Number:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "COD:", "Yes ___ No ___", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Payment Type:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Total Hours:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Engine Hours:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Electric Hours:", "", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Loaded:", "Yes ___ No ___", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Waiting:", "Yes ___ No ___", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Dropped:", "Yes ___ No ___", boldFont, normalFont);
            addBorderedTableRow(unitInfoTable, "Date Needed:", "", boldFont, normalFont);

            unitInfoCell.addElement(new Paragraph("UNIT INFORMATION", boldFont));
            unitInfoCell.addElement(new Paragraph(" "));
            unitInfoCell.addElement(unitInfoTable);
            middleSectionTable.addCell(unitInfoCell);

            document.add(middleSectionTable);
            document.add(new Paragraph(" "));

            // ===== MILEAGE & COMPLAINT SECTION =====
            PdfPTable mileageTable = new PdfPTable(2);
            mileageTable.setWidthPercentage(100);
            mileageTable.setWidths(new float[]{15, 85});
            addBorderedTableRow(mileageTable, "MILEAGE:", "", boldFont, normalFont);
            document.add(mileageTable);
            document.add(new Paragraph(" "));

            // COMPLAINT section
            PdfPTable complaintTable = new PdfPTable(1);
            complaintTable.setWidthPercentage(100);
            PdfPCell complaintCell = new PdfPCell();
            complaintCell.setBorder(Rectangle.BOX);
            complaintCell.setPadding(8);

            StringBuilder complaintText = new StringBuilder();
            complaintText.append("Location: ").append(vehicle.getIssueLocation()).append("\n");
            complaintText.append("Issue Type: ").append(vehicle.getIssueType()).append("\n");
            complaintText.append("Severity: ").append(vehicle.getSeverity()).append("\n");
            if (vehicle.getFreeFormText() != null && !vehicle.getFreeFormText().trim().isEmpty()) {
                complaintText.append("\nCustomer Comments: ").append(vehicle.getFreeFormText());
            }
            if (vehicle.getTranslatedText() != null && !vehicle.getTranslatedText().trim().isEmpty()) {
                complaintText.append("\n\nTranslated Comments: ").append(vehicle.getTranslatedText());
            }

            Paragraph complaintPara = new Paragraph(complaintText.toString(), normalFont);
            complaintCell.addElement(new Paragraph("COMPLAINT:", boldFont));
            complaintCell.addElement(new Paragraph(" "));
            complaintCell.addElement(complaintPara);
            complaintTable.addCell(complaintCell);
            document.add(complaintTable);
            document.add(new Paragraph(" "));

            // ===== AUTHORIZATION STATEMENT =====
            PdfPTable authTable = new PdfPTable(1);
            authTable.setWidthPercentage(100);
            PdfPCell authCell = new PdfPCell();
            authCell.setBorder(Rectangle.BOX);
            authCell.setPadding(8);

            String authText = "I hereby authorize the repair work to be done along with the necessary materials. " +
                    "You and your employees may operate vehicle for purposes of testing, inspection or delivery at my risk. " +
                    "An express mechanic's lien is acknowledged on vehicle to secure the amount of repairs thereto. " +
                    "You will not be held responsible for loss or damage to vehicle or articles left in vehicle in case " +
                    "of fire, theft, accident or any other cause beyond your control.";

            authCell.addElement(new Paragraph(authText, FontFactory.getFont(FontFactory.HELVETICA, 9)));
            authTable.addCell(authCell);
            document.add(authTable);
            document.add(new Paragraph(" "));

            // ===== TOTAL COST SECTION =====
            PdfPTable totalTable = new PdfPTable(6);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{15, 15, 15, 15, 20, 20});

            String[] totalHeaders = {"Total Parts", "Labor", "Shop Supplies", "Env. Fees", "Subtotal", "Total"};
            for (String header : totalHeaders) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, boldFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(5);
                totalTable.addCell(headerCell);
            }

            for (int i = 0; i < 6; i++) {
                PdfPCell emptyCell = new PdfPCell(new Phrase("", normalFont));
                emptyCell.setPadding(8);
                totalTable.addCell(emptyCell);
            }

            document.add(totalTable);
            document.add(new Paragraph(" "));

            // ===== SIGNATURE LINE =====
            Paragraph signatureLine = new Paragraph("Customer Signature: _______________________________     Date: _____________", normalFont);
            signatureLine.setSpacingBefore(15);
            document.add(signatureLine);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF", e);
        }

        return out.toByteArray();
    }

    private void addBorderedTableRow(PdfPTable table, String label, String value, Font boldFont, Font normalFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, boldFont));
        labelCell.setPadding(4);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", normalFont));
        valueCell.setPadding(4);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}