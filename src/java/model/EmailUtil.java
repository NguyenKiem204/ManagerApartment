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
        final String senderPassword = "y d w p z p n i c b e c b c l v"; //(App Password)

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

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Resident's account profile");
            message.setText("Hey,\n\nYour account was created.\nYour password is: " + password + "\n\n Please change your password when you log in by this email!");

            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailSuccessPayment(String recipientEmail, Invoices invoice) {
        final String senderEmail = "dungnqhe186457@fpt.edu.vn";
        final String senderPassword = "y d w p z p n i c b e c b c l v";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Invoice Payment Successfully");

            // Chuyển danh sách chi tiết hóa đơn thành HTML
            StringBuilder invoiceDetailsHtml = new StringBuilder();
            int index = 1;
            for (InvoiceDetail detail : invoice.getDetails()) {
                invoiceDetailsHtml.append("<tr>")
                        .append("<td>").append(index++).append("</td>")
                        .append("<td>").append(detail.getDescription()).append("</td>")
                        .append("<td>$").append(detail.getAmount()).append("</td>")
                        .append("<td>").append(detail.getBillType()).append("</td>")
                        .append("</tr>");
            }

            String emailContent = "<div class='container mt-5'>"
                    + "<div class='card p-4 shadow-lg rounded-4' style='max-width: 1000px; margin: 0 auto;'>"
                    + "<h3 class='text-center mb-4'>Invoice Details</h3>"
                    + "<div class='mb-3'><h2>Invoice Code: " + invoice.getInvoiceID() + "</h2></div>"
                    + "<div class='mb-3'><strong>Apartment:</strong> " + invoice.getApartment().getApartmentName() + "</div>"
                    + "<div class='mb-3'><strong>Resident:</strong> " + invoice.getResident().getFullName() + "</div>"
                    + "<div class='mb-3'><strong>Description:</strong> " + invoice.getDescription() + "</div>"
                    + "<div class='mb-3'><strong>Public Date:</strong> " + invoice.getPublicDateft() + "</div>"
                    + "<div class='mb-3'><strong>Due Date:</strong> " + invoice.getDueDateft() + "</div>"
                    + (invoice.getStatus().equals("Paid") ? "<div class='mb-3'><strong>Pay Date:</strong> " + invoice.getPaydateft() + "</div>" : "")
                    + "<div class='mb-3'><strong>Status:</strong> "
                    + "<span class='badge bg-" + (invoice.getStatus().equals("Paid") ? "success" : "warning") + "'>"
                    + invoice.getStatus() + "</span></div>"
                    + "<h5 class='mt-4'>Invoice Details</h5>"
                    + "<table class='table table-bordered mt-3'>"
                    + "<thead>"
                    + "<tr><th>#</th><th>Description</th><th>Amount</th><th>Type Bill</th></tr>"
                    + "</thead>"
                    + "<tbody>"
                    + invoiceDetailsHtml
                    + "</tbody>"
                    + "</table>"
                    + (invoice.getMuon() > 0 ? "<div class='mb-3'><strong>Late Bill Penalty:</strong> $" + invoice.getMuon() + "</div>" : "")
                    + "<div class='mb-3'><strong>Total Amount:</strong> $" + invoice.getTotalAmount() + "</div>"
                    + "<hr>"
                    + "<p class='text-center'>Thank you for your payment!</p>"
                    + "</div>"
                    + "</div>";

            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void sendEmailRenewContract(String recipientEmail, String subject, String messageText) {
        final String senderEmail = "dungnqhe186457@fpt.edu.vn"; // 
        final String senderPassword = "y d w p z p n i c b e c b c l v";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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
