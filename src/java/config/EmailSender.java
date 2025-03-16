package config;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

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
}
