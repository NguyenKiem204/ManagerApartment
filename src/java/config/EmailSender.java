package config;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.time.Instant;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import model.UtilityBill;

public class EmailSender {

    public void sendVerificationEmail(HttpServletRequest request, String toEmail) throws MessagingException, UnsupportedEncodingException {

        String token = generateToken(toEmail);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        Date expirationTime = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String formattedExpirationTime = dateFormat.format(expirationTime);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("buildingmanagement77@gmail.com", "hpfyyhbaelpgdeir");
            }
        });

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String verificationLink = baseUrl + "/verify?email=" + toEmail + "&token=" + token;

        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #333;'>Đổi Mật Khẩu</h2>"
                + "<p>Vui lòng nhấn vào nút bên dưới để xác minh tài khoản:</p>"
                + "<a href='" + verificationLink + "' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 10px 0; border-radius: 5px;'>Click Here</a>"
                + "<p>Token sẽ hết hạn vào lúc: <strong>" + formattedExpirationTime + "</strong></p>"
                + "</body>"
                + "</html>";

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("buildingmanagement77@gmail.com", "Building Management"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Đổi Mật Khẩu", "UTF-8");

        message.setContent(htmlContent, "text/html; charset=UTF-8");
        Transport.send(message);
    }

    private String generateToken(String email) {
        long timestamp = Instant.now().getEpochSecond();
        String rawData = email + ":" + timestamp;
        return BCrypt.withDefaults().hashToString(10, rawData.toCharArray());
    }

public boolean sendUtilityBillEmail(String toEmail, UtilityBill bill, byte[] pdfBytes) {
    try {
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("buildingmanagement77@gmail.com", "hpfyyhbaelpgdeir");
            }
        });
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedAmount = currencyFormat.format(bill.getTotalAmount());

        
        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #333;'>Hóa Đơn Tiện Ích - Tháng " + bill.getBillingMonth() + "/" + bill.getBillingYear() + "</h2>"
                + "<p>Kính gửi: <strong>" + bill.getOwner().getFullName() + "</strong>,</p>"
                + "<p>Đính kèm là hóa đơn tiện ích của căn hộ " + bill.getApartment().getApartmentName()
                + " cho kỳ thanh toán tháng " + bill.getBillingMonth() + "/" + bill.getBillingYear() + ".</p>"
                + "<p>Tổng số tiền cần thanh toán: <strong>" + formattedAmount + "</strong></p>"
                + "<p>Vui lòng thanh toán trước ngày: <strong>" + bill.getFormattedDueDate() + "</strong></p>"
                + "<p>Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ với ban quản lý tòa nhà.</p>"
                + "<p>Trân trọng,<br>Ban Quản Lý</p>"
                + "</body>"
                + "</html>";

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("buildingmanagement77@gmail.com", "Ban Quản Lý Tòa Nhà", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Hóa Đơn Tiện Ích - Tháng " + bill.getBillingMonth() + "/" + bill.getBillingYear(), "UTF-8");
        Multipart multipart = new MimeMultipart();
        
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlContent, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);
        
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setContent(pdfBytes, "application/pdf");
        attachmentPart.setFileName("HoaDon_" + bill.getBillingMonth() + "_" + bill.getBillingYear() + "_" + bill.getApartment().getApartmentName() + ".pdf");
        multipart.addBodyPart(attachmentPart);
        message.setContent(multipart);
        Transport.send(message);
        return true;
    } catch (MessagingException me) {
        me.printStackTrace();
        return false;
    } catch (UnsupportedEncodingException ue) {
        ue.printStackTrace();
        return false;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
