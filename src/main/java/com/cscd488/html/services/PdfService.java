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

            Paragraph topSpacer = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 12));
            topSpacer.setSpacingAfter(20);
            document.add(topSpacer);

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

            PdfPTable topSection = new PdfPTable(2);
            topSection.setWidthPercentage(100);
            topSection.setWidths(new float[]{50, 50});

            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(Rectangle.BOX);
            customerCell.setBorderWidth(1);
            customerCell.setPadding(3);

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setWidths(new float[]{35, 65});

            addCellWithBorders(customerTable, "Company Name:", getValueOrBlank(customer.getCompanyName()), boldFont, normalFont);
            addCellWithBorders(customerTable, "Driver's Name:", customer.getFname() + " " + customer.getLname(), boldFont, normalFont);
            addCellWithBorders(customerTable, "Driver's Number:", customer.getPhone(), boldFont, normalFont);
            addCellWithBorders(customerTable, "Email:", customer.getEmail(), boldFont, normalFont);
            addCellWithBorders(customerTable, "Address:", customer.getAddress(), boldFont, normalFont);
            addCellWithBorders(customerTable, "Reference Number:", orderNumber, boldFont, normalFont);
            addCellWithBorders(customerTable, "Purchase Order:", "_________________", boldFont, normalFont);

            customerCell.addElement(new Paragraph("CUSTOMER INFORMATION", titleFont));
            customerCell.addElement(new Paragraph(" "));
            customerCell.addElement(customerTable);
            topSection.addCell(customerCell);

            PdfPCell techCell = new PdfPCell();
            techCell.setBorder(Rectangle.BOX);
            techCell.setBorderWidth(1);
            techCell.setPadding(3);

            PdfPTable techTable = new PdfPTable(2);
            techTable.setWidthPercentage(100);
            techTable.setWidths(new float[]{35, 65});

            addCellWithBorders(techTable, "Employee Name:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Employee #:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Authorization Person:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Vehicle #:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Travel Mileage:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Time on Job:", "_________________", boldFont, normalFont);
            addCellWithBorders(techTable, "Start:", "_________", boldFont, normalFont);
            addCellWithBorders(techTable, "End:", "_________", boldFont, normalFont);

            techCell.addElement(new Paragraph("TECHNICIAN INFO", titleFont));
            techCell.addElement(new Paragraph(" "));
            techCell.addElement(techTable);
            topSection.addCell(techCell);

            document.add(topSection);

            PdfPTable middleSection = new PdfPTable(2);
            middleSection.setWidthPercentage(100);
            middleSection.setWidths(new float[]{50, 50});

            PdfPCell vehicleCell = new PdfPCell();
            vehicleCell.setBorder(Rectangle.BOX);
            vehicleCell.setBorderWidth(1);
            vehicleCell.setPadding(3);

            PdfPTable vehicleTable = new PdfPTable(2);
            vehicleTable.setWidthPercentage(100);
            vehicleTable.setWidths(new float[]{35, 65});

            addCellWithBorders(vehicleTable, "Manufacture:", vehicle.getMake(), boldFont, normalFont);
            addCellWithBorders(vehicleTable, "Model:", vehicle.getModel(), boldFont, normalFont);
            addCellWithBorders(vehicleTable, "Year:", vehicle.getYear(), boldFont, normalFont);
            addCellWithBorders(vehicleTable, "VIN:", vehicle.getVin(), boldFont, normalFont);
            addCellWithBorders(vehicleTable, "License Plate:", getValueOrBlank(vehicle.getLicensePlate()), boldFont, normalFont);
            addCellWithBorders(vehicleTable, "Current Mileage:", getValueOrBlank(vehicle.getMileage()), boldFont, normalFont);

            vehicleCell.addElement(new Paragraph("VEHICLE INFORMATION", titleFont));
            vehicleCell.addElement(new Paragraph(" "));
            vehicleCell.addElement(vehicleTable);
            middleSection.addCell(vehicleCell);

            PdfPCell unitCell = new PdfPCell();
            unitCell.setBorder(Rectangle.BOX);
            unitCell.setBorderWidth(1);
            unitCell.setPadding(3);

            PdfPTable unitTable = new PdfPTable(2);
            unitTable.setWidthPercentage(100);
            unitTable.setWidths(new float[]{40, 60});

            addCellWithBorders(unitTable, "Unit Number:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Unit Type:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "BM Number:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Serial Number:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "COD:", "Yes ___ No ___", boldFont, normalFont);
            addCellWithBorders(unitTable, "Payment Type:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Total Hours:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Engine Hours:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Electric Hours:", "_________________", boldFont, normalFont);
            addCellWithBorders(unitTable, "Loaded:", "Yes ___ No ___", boldFont, normalFont);
            addCellWithBorders(unitTable, "Waiting:", "Yes ___ No ___", boldFont, normalFont);
            addCellWithBorders(unitTable, "Dropped:", "Yes ___ No ___", boldFont, normalFont);
            addCellWithBorders(unitTable, "Date Needed:", "_____________", boldFont, normalFont);

            unitCell.addElement(new Paragraph("UNIT INFORMATION", titleFont));
            unitCell.addElement(new Paragraph(" "));
            unitCell.addElement(unitTable);
            middleSection.addCell(unitCell);

            document.add(middleSection);

            PdfPTable mileageTable = new PdfPTable(2);
            mileageTable.setWidthPercentage(100);
            mileageTable.setWidths(new float[]{15, 85});
            addCellWithBorders(mileageTable, "MILEAGE:", getValueOrBlank(vehicle.getMileage()), boldFont, normalFont);
            document.add(mileageTable);

            PdfPTable complaintTable = new PdfPTable(1);
            complaintTable.setWidthPercentage(100);
            PdfPCell complaintCell = new PdfPCell();
            complaintCell.setBorder(Rectangle.BOX);
            complaintCell.setBorderWidth(1);
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

            complaintCell.addElement(new Paragraph(" "));
            complaintCell.addElement(new Paragraph("Technician Notes: _______________________________________________", normalFont));
            complaintCell.addElement(new Paragraph("_______________________________________________________________", normalFont));

            complaintTable.addCell(complaintCell);
            document.add(complaintTable);

            PdfPTable authTable = new PdfPTable(1);
            authTable.setWidthPercentage(100);
            PdfPCell authCell = new PdfPCell();
            authCell.setBorder(Rectangle.BOX);
            authCell.setBorderWidth(1);
            authCell.setPadding(3);

            String authText = "I hereby authorize the repair work to be done along with the necessary materials. " +
                    "You and your employees may operate vehicle for purposes of testing, inspection or delivery at my risk. " +
                    "An express mechanic's lien is acknowledged on vehicle to secure the amount of repairs thereto. " +
                    "You will not be held responsible for loss or damage to vehicle or articles left in vehicle in case of fire, theft, accident or any other cause beyond your control.";

            authCell.addElement(new Paragraph(authText, FontFactory.getFont(FontFactory.HELVETICA, 7)));
            authTable.addCell(authCell);
            document.add(authTable);

            PdfPTable totalTable = new PdfPTable(6);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{16, 16, 16, 16, 18, 18});

            String[] totalHeaders = {"Parts", "Labor", "Shop Supplies", "Env. Fees", "Subtotal", "Total"};
            for (String header : totalHeaders) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, boldFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(3);
                headerCell.setBorder(Rectangle.BOX);
                headerCell.setBorderWidth(1);
                totalTable.addCell(headerCell);
            }

            for (int i = 0; i < 6; i++) {
                PdfPCell emptyCell = new PdfPCell(new Phrase("", normalFont));
                emptyCell.setPadding(4);
                emptyCell.setBorder(Rectangle.BOX);
                emptyCell.setBorderWidth(1);
                totalTable.addCell(emptyCell);
            }

            document.add(totalTable);

            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setWidths(new float[]{50, 50});

            PdfPCell customerSigCell = new PdfPCell();
            customerSigCell.setBorder(Rectangle.BOX);
            customerSigCell.setBorderWidth(1);
            customerSigCell.setPadding(5);
            customerSigCell.addElement(new Paragraph("Customer Signature: _______________________________", normalFont));
            customerSigCell.addElement(new Paragraph("Date: _____________", normalFont));
            signatureTable.addCell(customerSigCell);

            PdfPCell techSigCell = new PdfPCell();
            techSigCell.setBorder(Rectangle.BOX);
            techSigCell.setBorderWidth(1);
            techSigCell.setPadding(5);
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

    private void addCellWithBorders(PdfPTable table, String label, String value, Font boldFont, Font normalFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, boldFont));
        labelCell.setPadding(3);
        labelCell.setBorder(Rectangle.BOX);
        labelCell.setBorderWidth(1);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "", normalFont));
        valueCell.setPadding(3);
        valueCell.setBorder(Rectangle.BOX);
        valueCell.setBorderWidth(1);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private String getValueOrBlank(String value) {
        return (value != null && !value.isEmpty()) ? value : "_________________";
    }
}