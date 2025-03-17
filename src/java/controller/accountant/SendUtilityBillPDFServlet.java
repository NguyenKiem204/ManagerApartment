//package controller.accountant;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import dao.UtilityBillDAO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.text.NumberFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.Locale;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import model.UtilityBill;
//import config.EmailSender;
//import jakarta.mail.MessagingException;
//
//@WebServlet(name = "SendUtilityBillPDFServlet", urlPatterns = {"/accountant/send-utility-bill-pdf"})
//public class SendUtilityBillPDFServlet extends HttpServlet {
//
//    private static final Logger LOGGER = Logger.getLogger(SendUtilityBillPDFServlet.class.getName());
//    private UtilityBillDAO utilityBillDAO;
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        utilityBillDAO = new UtilityBillDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            int billId = Integer.parseInt(request.getParameter("billId"));
//            
//            // Get the bill details from database
//            UtilityBill bill = utilityBillDAO.getBillDetails(billId);
//            
//            if (bill == null) {
//                String errorMessage = "Không tìm thấy hóa đơn với ID: " + billId;
//                response.sendRedirect(request.getContextPath() + 
//                        "/accountant/view-utility-bills?error=" + 
//                        java.net.URLEncoder.encode(errorMessage, "UTF-8"));
//                return;
//            }
//            
//            // Generate PDF document
//            byte[] pdfBytes = generatePDF(bill);
//            
//            // Get owner's email
//            String ownerEmail = bill.getOwner().getEmail();
//            if (ownerEmail == null || ownerEmail.trim().isEmpty()) {
//                String errorMessage = "Chủ căn hộ không có email";
//                response.sendRedirect(request.getContextPath() + 
//                        "/accountant/view-utility-bill?id=" + billId + "&error=" + 
//                        java.net.URLEncoder.encode(errorMessage, "UTF-8"));
//                return;
//            }
//            
//            // Send email with PDF attachment
//            EmailSender emailSender = new EmailSender();
//            boolean sent = emailSender.sendUtilityBillEmail(ownerEmail, bill, pdfBytes);
//            
//            if (sent) {
//                String successMessage = "Đã gửi hóa đơn qua email thành công cho " + bill.getOwner().getFullName();
//                response.sendRedirect(request.getContextPath() + 
//                        "/accountant/view-utility-bill?id=" + billId + "&success=" + 
//                        java.net.URLEncoder.encode(successMessage, "UTF-8"));
//            } else {
//                String errorMessage = "Không thể gửi email";
//                response.sendRedirect(request.getContextPath() + 
//                        "/accountant/view-utility-bill?id=" + billId + "&error=" + 
//                        java.net.URLEncoder.encode(errorMessage, "UTF-8"));
//            }
//            
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Error sending utility bill PDF", e);
//            String errorMessage = "Lỗi khi gửi hóa đơn: " + e.getMessage();
//            response.sendRedirect(request.getContextPath() + 
//                    "/accountant/view-utility-bills?error=" + 
//                    java.net.URLEncoder.encode(errorMessage, "UTF-8"));
//        }
//    }
//    
//    private byte[] generatePDF(UtilityBill bill) throws DocumentException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, baos);
//        
//        document.open();
//        
//        // Font settings
//        BaseFont baseFont = null;
//        try {
//            baseFont = BaseFont.createFont("font/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        } catch (IOException e) {
//            baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
//        }
//        
//        Font titleFont = new Font(baseFont, 18, Font.BOLD);
//        Font subtitleFont = new Font(baseFont, 14, Font.BOLD);
//        Font normalFont = new Font(baseFont, 12, Font.NORMAL);
//        Font smallFont = new Font(baseFont, 10, Font.NORMAL);
//        Font boldFont = new Font(baseFont, 12, Font.BOLD);
//        
//        // Title
//        Paragraph title = new Paragraph("HÓA ĐƠN TIỆN ÍCH", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        
//        Paragraph billId = new Paragraph("Mã hóa đơn: " + bill.getBillId(), subtitleFont);
//        billId.setAlignment(Element.ALIGN_CENTER);
//        document.add(billId);
//        
//        document.add(new Paragraph(" "));
//        
//        // Billing period
//        PdfPTable periodTable = new PdfPTable(2);
//        periodTable.setWidthPercentage(100);
//        
//        periodTable.addCell(createCell("Kỳ thanh toán:", normalFont, false));
//        periodTable.addCell(createCell("Tháng " + bill.getBillingMonth() + "/" + bill.getBillingYear(), normalFont, false));
//        
//        periodTable.addCell(createCell("Từ ngày:", normalFont, false));
//        periodTable.addCell(createCell(bill.getFormattedBillingPeriodStart(), normalFont, false));
//        
//        periodTable.addCell(createCell("Đến ngày:", normalFont, false));
//        periodTable.addCell(createCell(bill.getFormattedBillingPeriodEnd(), normalFont, false));
//        
//        document.add(periodTable);
//        document.add(new Paragraph(" "));
//        
//        // Apartment & Owner info
//        PdfPTable infoTable = new PdfPTable(2);
//        infoTable.setWidthPercentage(100);
//        
//        infoTable.addCell(createCell("THÔNG TIN CĂN HỘ", subtitleFont, true));
//        infoTable.addCell(createCell("THÔNG TIN CHỦ HỘ", subtitleFont, true));
//        
//        infoTable.addCell(createCell("Tên căn hộ: " + bill.getApartment().getApartmentName(), normalFont, false));
//        infoTable.addCell(createCell("Họ tên: " + bill.getOwner().getFullName(), normalFont, false));
//        
//        infoTable.addCell(createCell("Block: " + bill.getApartment().getBlock(), normalFont, false));
//        infoTable.addCell(createCell("Số điện thoại: " + bill.getOwner().getPhoneNumber(), normalFont, false));
//        
//        infoTable.addCell(createCell("Loại căn hộ: " + bill.getApartment().getType(), normalFont, false));
//        infoTable.addCell(createCell("Email: " + bill.getOwner().getEmail(), normalFont, false));
//        
//        document.add(infoTable);
//        document.add(new Paragraph(" "));
//        
//        // Bill details
//        Paragraph consumptionTitle = new Paragraph("CHI TIẾT TIÊU THỤ", subtitleFont);
//        consumptionTitle.setAlignment(Element.ALIGN_CENTER);
//        document.add(consumptionTitle);
//        document.add(new Paragraph(" "));
//        
//        // Format currency
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        
//        PdfPTable detailsTable = new PdfPTable(3);
//        detailsTable.setWidthPercentage(100);
//        float[] columnWidths = {4f, 3f, 3f};
//        detailsTable.setWidths(columnWidths);
//        
//        detailsTable.addCell(createCell("Dịch vụ", boldFont, true));
//        detailsTable.addCell(createCell("Lượng tiêu thụ", boldFont, true));
//        detailsTable.addCell(createCell("Thành tiền", boldFont, true));
//        
//        detailsTable.addCell(createCell("Điện", normalFont, false));
//        detailsTable.addCell(createCell(bill.getElectricityConsumption() + " kWh", normalFont, false));
//        detailsTable.addCell(createCell(currencyFormat.format(bill.getElectricityCost()), normalFont, false));
//        
//        detailsTable.addCell(createCell("Nước", normalFont, false));
//        detailsTable.addCell(createCell(bill.getWaterConsumption() + " m³", normalFont, false));
//        detailsTable.addCell(createCell(currencyFormat.format(bill.getWaterCost()), normalFont, false));
//        
//        detailsTable.addCell(createCell("Tổng cộng", boldFont, true));
//        detailsTable.addCell(createCell("", boldFont, true));
//        detailsTable.addCell(createCell(currencyFormat.format(bill.getTotalAmount()), boldFont, true));
//        
//        document.add(detailsTable);
//        document.add(new Paragraph(" "));
//        
//        // Payment info
//        PdfPTable paymentTable = new PdfPTable(2);
//        paymentTable.setWidthPercentage(100);
//        
//        paymentTable.addCell(createCell("Ngày tạo hóa đơn:", normalFont, false));
//        paymentTable.addCell(createCell(bill.getFormattedGeneratedDate(), normalFont, false));
//        
//        paymentTable.addCell(createCell("Ngày đáo hạn thanh toán:", boldFont, false));
//        paymentTable.addCell(createCell(bill.getFormattedDueDate(), boldFont, false));
//        
//        paymentTable.addCell(createCell("Trạng thái:", normalFont, false));
//        paymentTable.addCell(createCell(bill.getStatus(), normalFont, false));
//        
//        document.add(paymentTable);
//        document.add(new Paragraph(" "));
//        
//        // Footer
//        Paragraph footer = new Paragraph("Vui lòng thanh toán đúng hạn để tránh phí phạt. "
//                + "Liên hệ ban quản lý nếu có bất kỳ thắc mắc nào.", smallFont);
//        footer.setAlignment(Element.ALIGN_CENTER);
//        document.add(footer);
//        
//        document.close();
//        return baos.toByteArray();
//    }
//    
//    private PdfPCell createCell(String text, Font font, boolean isHeader) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, font));
//        if (isHeader) {
////            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        }
//        cell.setPadding(5);
//        return cell;
//    }
//}