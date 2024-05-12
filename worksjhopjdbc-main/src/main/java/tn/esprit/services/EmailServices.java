package tn.esprit.services;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailServices {

    private final String username;
    private final String password;
    private final Properties properties;

    public EmailServices() {
        this.username = "mannaiomar28@gmail.com";
        this.password = "HtLJFb5fyOvk2aRV";

        // Configure email properties
        this.properties = new Properties();
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.starttls.enable", "true");
        this.properties.put("mail.smtp.host", "smtp-relay.brevo.com");
        this.properties.put("mail.smtp.port", "587");
    }

    public void sendEmail(String to, String post) throws MessagingException, IOException {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create a multipart message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("2rism@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("New blog");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlContent = "<html><body>";
        htmlContent += "<div style='text-align:center;'>";
        htmlContent += "<img src='cid:email1' />";
        htmlContent += "<h1>"+post+"</h1>";
        htmlContent += "<img src='cid:email2' />";
        htmlContent += "</div>";
        htmlContent += "</body></html>";
        messageBodyPart.setContent(htmlContent, "text/html");



        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart imagePart1 = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:/Users/user/Desktop/2rism hbib/worksjhopjdbc-main/src/main/resources/img1.jpg");
        imagePart1.setDataHandler(new DataHandler(fds));
        imagePart1.setHeader("Content-ID", "<email1>");


        MimeBodyPart imagePart2 = new MimeBodyPart();
        DataSource fds3 = new FileDataSource("C:/Users/user/Desktop/2rism hbib/worksjhopjdbc-main/src/main/resources/img2.jpg");
        imagePart2.setDataHandler(new DataHandler(fds3));
        imagePart2.setHeader("Content-ID", "<email2>");

        multipart.addBodyPart(imagePart1);
        multipart.addBodyPart(imagePart2);

        // Set the multipart as the content of the message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully to: " + to);
    }



}
