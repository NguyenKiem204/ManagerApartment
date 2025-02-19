/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    public static void sendEmail(String recipientEmail, String password) {
         final String senderEmail = "dungnqhe186457@fpt.edu.vn"; // 
         final String senderPassword = "v x w o x l k e d g e e x u a e"; //(App Password)

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");

        // TLS 587 SSL 465
        props.put("mail.smtp.port", "587");

        // dang nhap
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject("Resident's account profile");
//            message.setText("Hey,\n\nYour account was created.\nYour password is: " + password + "\n\n Please change your password when you log in by this email!");
//
//            Transport.send(message);
//            System.out.println("Email đã được gửi thành công!");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }
    public boolean isValidEmail(String email) {
        // Biểu thức chính quy cho định dạng email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        // Tạo đối tượng Pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Tạo đối tượng Matcher
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra chuỗi với biểu thức chính quy
        return matcher.matches();
    }
}