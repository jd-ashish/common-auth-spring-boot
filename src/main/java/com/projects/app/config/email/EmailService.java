package com.projects.app.config.email;


import org.springframework.mail.javamail.JavaMailSenderImpl;

// Interface
public interface EmailService {

    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    String sendHtmlMail(String USER_EMAIL ,String to ,String subject ,String content);
    String sendHtmlMailWithConfig(String cc, String bcc,JavaMailSenderImpl javaMailSender,
                                  String USER_EMAIL , String to , String subject , String content);

    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

}