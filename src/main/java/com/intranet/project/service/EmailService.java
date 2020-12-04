package com.intranet.project.service;


import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {



    public Session createSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("awesomeprojectintranet", "Parool11");
                    }
                });
        return session;
    }

    public void sendEmail(Session s, String sendToEmail) throws MessagingException {
        Session session = createSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("awesomeprojectintranet@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(sendToEmail)
        );
        message.setSubject("Password recovery");
        String recoveryLink = "aaa";
        String body = "Hi, here's a link to your password recovery: " + recoveryLink;
        message.setText("Vali IT test");
        Transport.send(message);
    }


}
