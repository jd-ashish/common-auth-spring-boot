package com.projects.app.config.email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {


    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.password}")
    private String EMAIL_PASSWORD;

    @Value("${spring.mail.host}")
    private String EMAIL_HOST;

    @Value("${spring.mail.transport.protocol}")
    private String EMAIL_PROTOCOL;
    @Value("${spring.mail.port}")
    private Integer EMAIL_PORT;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean EMAIL_AUTH;

    @Value("${spring.mail.smtp.ssl.enable}")
    private boolean EMAIL_SSL_ENABLE;


    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            emailConfig().send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail " + e.getMessage();
        }
    }


    @Override
    public String sendHtmlMail(String USER_EMAIL, String to, String subject, String content) {
        sendHtmlMail(emailConfig(), content, USER_EMAIL, to, subject, null, null);
        return "Mail Sent Successfully...";
    }

    public void sendHtmlMail(JavaMailSenderImpl mailSender,
                             String content, String USER_EMAIL, String to, String subject,
                             String[] cc, String[] bcc) {

        MimeMessage mimeMessage = emailConfig().createMimeMessage();
        // Try block to check for exceptions
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            messageHelper.setFrom(USER_EMAIL);
            messageHelper.setTo(to);
            if (bcc != null || cc != null) {
                messageHelper.setCc(cc);
                messageHelper.setBcc(bcc);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            emailConfig().send(mimeMessage);
        } catch (Exception ex) {
            throw new RuntimeException("Email could not be sent to user '{}': {}" + ex.getMessage());
        }
    }

    @Override
    public String sendHtmlMailWithConfig(String cc, String bcc, JavaMailSenderImpl mailSender, String USER_EMAIL, String to, String subject, String content) {
        sendHtmlMail(mailSender, content, USER_EMAIL, to, subject, cc.split(","), bcc.split(","));
        return "Mail Sent Successfully...";
    }


    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage = emailConfig().createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            emailConfig().send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }

    private JavaMailSenderImpl emailConfig() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EMAIL_HOST);
        mailSender.setPort(EMAIL_PORT);
        mailSender.setUsername(sender);
        mailSender.setPassword(EMAIL_PASSWORD);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", Boolean.TRUE);
        properties.put("mail.transport.protocol", EMAIL_PROTOCOL);
        properties.put("mail.smtp.auth", EMAIL_AUTH);
        properties.put("mail.smtp.starttls.required", Boolean.TRUE);
        properties.put("mail.smtp.ssl.enable", EMAIL_SSL_ENABLE);
        properties.put("mail.test-connection", Boolean.TRUE);
        properties.put("mail.debug", Boolean.TRUE);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}
