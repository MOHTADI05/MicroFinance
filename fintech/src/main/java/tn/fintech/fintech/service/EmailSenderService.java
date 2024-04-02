package tn.fintech.fintech.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.fintech.fintech.serviceInterface.InterfaceEmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
@AllArgsConstructor
@Service
public class EmailSenderService implements InterfaceEmailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    )
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mohtadimarmouri@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        try {
            mailSender.send(message);
            System.out.println("Mail Sent Successfully.");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}
