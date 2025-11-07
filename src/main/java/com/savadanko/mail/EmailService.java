package com.savadanko.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            System.out.println(">>> Try send email FROM " + fromEmail + " TO " + to);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            System.out.println(">>> Email send() finished without exception");
        } catch (Exception e) {
            System.out.println(">>> Email send FAILED: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void sendMimeEmail(
            String to,
            String subject,
            String text,
            String pathToAttachment) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            Resource file = new ClassPathResource(pathToAttachment);
            helper.addAttachment("Invoice", file);

            mailSender.send(mimeMessage);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


