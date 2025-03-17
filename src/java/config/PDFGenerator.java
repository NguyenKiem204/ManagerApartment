/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import model.UtilityBill;

/**
 *
 * @author nkiem
 */
public class PDFGenerator {

    public static byte[] generateUtilityBillPDF(UtilityBill bill) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();
        BaseFont baseFont = null;

        try {
            String fontPath = System.getProperty("font.path", "D:/PR2/ManagerApartment/web/assets/font/times.ttf");
            baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (IOException e) {
            baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Font subtitleFont = new Font(baseFont, 14, Font.BOLD);
        Font normalFont = new Font(baseFont, 12, Font.NORMAL);
        Font smallFont = new Font(baseFont, 10, Font.NORMAL);
        Font boldFont = new Font(baseFont, 12, Font.BOLD);

        // Title
        Paragraph title = new Paragraph("UTILITY BILL", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph billId = new Paragraph("Invoice Code: " + bill.getBillId(), subtitleFont);
        billId.setAlignment(Element.ALIGN_CENTER);
        document.add(billId);

        document.add(new Paragraph(" "));

        // Billing period
        PdfPTable periodTable = new PdfPTable(2);
        periodTable.setWidthPercentage(100);

        periodTable.addCell(createCell("Billing Period:", normalFont, false));
        periodTable.addCell(createCell("Month " + bill.getBillingMonth() + "/" + bill.getBillingYear(), normalFont, false));

        periodTable.addCell(createCell("From Date:", normalFont, false));
        periodTable.addCell(createCell(bill.getFormattedBillingPeriodStart(), normalFont, false));

        periodTable.addCell(createCell("To Date:", normalFont, false));
        periodTable.addCell(createCell(bill.getFormattedBillingPeriodEnd(), normalFont, false));

        document.add(periodTable);
        document.add(new Paragraph(" "));

        // Apartment & Owner info
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);

        infoTable.addCell(createCell("APARTMENT INFORMATION", subtitleFont, true));
        infoTable.addCell(createCell("HOUSEHOLD OWNER INFORMATION", subtitleFont, true));

        infoTable.addCell(createCell("Apartment Name: " + bill.getApartment().getApartmentName(), normalFont, false));
        infoTable.addCell(createCell("Full Name: " + bill.getOwner().getFullName(), normalFont, false));

        infoTable.addCell(createCell("Block: " + bill.getApartment().getBlock(), normalFont, false));
        infoTable.addCell(createCell("Phone Number: " + bill.getOwner().getPhoneNumber(), normalFont, false));

        infoTable.addCell(createCell("Apartment Type: " + bill.getApartment().getType(), normalFont, false));
        infoTable.addCell(createCell("Email: " + bill.getOwner().getEmail(), normalFont, false));

        document.add(infoTable);
        document.add(new Paragraph(" "));

        // Bill details
        Paragraph consumptionTitle = new Paragraph("CONSUMPTION DETAILS", subtitleFont);
        consumptionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(consumptionTitle);
        document.add(new Paragraph(" "));

        // Format currency
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        PdfPTable detailsTable = new PdfPTable(3);
        detailsTable.setWidthPercentage(100);
        float[] columnWidths = {4f, 3f, 3f};
        detailsTable.setWidths(columnWidths);

        detailsTable.addCell(createCell("Service", boldFont, true));
        detailsTable.addCell(createCell("Consumption Amount", boldFont, true));
        detailsTable.addCell(createCell("Total Cost", boldFont, true));

        detailsTable.addCell(createCell("Electricity", normalFont, false));
        detailsTable.addCell(createCell(bill.getElectricityConsumption() + " kWh", normalFont, false));
        detailsTable.addCell(createCell(currencyFormat.format(bill.getElectricityCost()), normalFont, false));

        detailsTable.addCell(createCell("Water", normalFont, false));
        detailsTable.addCell(createCell(bill.getWaterConsumption() + " m³", normalFont, false));
        detailsTable.addCell(createCell(currencyFormat.format(bill.getWaterCost()), normalFont, false));

        detailsTable.addCell(createCell("Total", boldFont, true));
        detailsTable.addCell(createCell("", boldFont, true));
        detailsTable.addCell(createCell(currencyFormat.format(bill.getTotalAmount()), boldFont, true));

        document.add(detailsTable);
        document.add(new Paragraph(" "));

        // Payment info
        PdfPTable paymentTable = new PdfPTable(2);
        paymentTable.setWidthPercentage(100);

        paymentTable.addCell(createCell("Invoice Creation Date:", normalFont, false));
        paymentTable.addCell(createCell(bill.getFormattedGeneratedDate(), normalFont, false));

        paymentTable.addCell(createCell("Payment Due Date:", boldFont, false));
        paymentTable.addCell(createCell(bill.getFormattedDueDate(), boldFont, false));

        paymentTable.addCell(createCell("Status:", normalFont, false));
        paymentTable.addCell(createCell(bill.getStatus(), normalFont, false));

        document.add(paymentTable);
        document.add(new Paragraph(" "));

        Paragraph footer = new Paragraph("Please make the payment on time to avoid late fees.. "
                + "Please contact the management office if you have any questions.", smallFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();

        return baos.toByteArray();
    }

    private static PdfPCell createCell(String text, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        cell.setPadding(5);
        return cell;
    }
}
