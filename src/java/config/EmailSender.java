package config;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.time.Instant;

public class EmailSender {

    public void sendVerificationEmail(String toEmail) throws MessagingException {
        // Tạo token với BCrypt
        String token = generateToken(toEmail);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Đăng nhập email gửi
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nkiem347@gmail.com", "ubrayjmltzfkzzja");
            }
        });

        // Tạo nội dung email
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("nkiem347@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Xác minh tài khoản của bạn", "UTF-8");

        message.setContent("Vui lòng nhấn vào đường dẫn sau để xác minh tài khoản: "
                + "http://localhost:8080/ManagerApartment/verify?email=" + toEmail + "&token=" + token,
                "text/html; charset=UTF-8");

        Transport.send(message);
    }

    // Hàm tạo token sử dụng BCrypt
    private String generateToken(String email) {
        long timestamp = Instant.now().getEpochSecond(); // Lấy thời gian hiện tại (giây)
        String rawData = email + ":" + timestamp; // Kết hợp email và thời gian
        return BCrypt.withDefaults().hashToString(10, rawData.toCharArray());
    }
}
