package com.intranet.project.service;


import com.intranet.project.exceptions.InternalServerErrorException;
import com.intranet.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


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

    public void sendEmail(Session session, String sendToEmail, String url) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("awesomeprojectintranet@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(sendToEmail)
        );
        message.setSubject("Password recovery");
        String body = "Hi, here's a link to your password recovery: " + url;
        message.setText(body);
        Transport.send(message);
    }

    public void saveUUIDToBase(Long userId, String uuid){
        userRepository.saveUUIDToBase(userId, uuid);
    }

    public String resetUserPw(String uuid, String password) {
        Long userId = getUserIdForUUID(uuid);
        if(userRepository.updateUserPassword(userId, userService.savePassword(password)) == 1){
            userRepository.removeUUID(userId);
            return "Password renewed";
        } else {
            throw new InternalServerErrorException("Changing password failed");
        }
    }

    public Long getUserIdForUUID(String uuid) {
        return userRepository.getUserIdForUUID(uuid);
    }

}
