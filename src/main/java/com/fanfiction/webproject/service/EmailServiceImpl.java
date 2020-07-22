package com.fanfiction.webproject.service;

import com.fanfiction.webproject.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationEmailToken(String email, String emailVerificationToken, HttpServletRequest request) {
        String domainUrl = getDomainUrl(request);
        SimpleMailMessage mailMessage = constructEmailMessage(email, emailVerificationToken, domainUrl);
        emailSender.send(mailMessage);
    }

    private SimpleMailMessage constructEmailMessage(final String recipientEmail, final String token,  final String domainUrl) {
        final String subject = "Registration Confirmation";
        final String confirmationUrl = domainUrl + "/email-verification/" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText("Please validate your email. Link: " + " \r\n" + confirmationUrl);
        return email;
    }

    private String getDomainUrl(HttpServletRequest request) {
        return  new StringBuilder().append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":3000")
                .toString();
    }

}
