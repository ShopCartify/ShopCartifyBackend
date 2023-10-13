package com.shopcartify.mailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
            log.info("Email sent successfully to {}", to);
        }catch (MessagingException e){
            log.error("Unable to send to {} || Error :: {}", to, e.getMessage());
        }
    }
    @Override
    public void sendEmailForRegistration(String to, String subject, String html) throws MessagingException, UnsupportedEncodingException {
        String senderName = "ShopCartify";

        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("osisiogubenjamin1@gmail.com", senderName);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html,true);
        javaMailSender.send(message);
    }
    @Override
    public void sendEmailForNewProduct(String imageUrl, String productName, String supermarketName, String userEmail) throws MessagingException, UnsupportedEncodingException {
        String senderName = "ShopCartify";
        String subject = "New Product Added";
        String successMessage = "The QR code for "+productName+" has been generated successfully!";

//        String mailContent =String.format( "<p>"+ "<img src="+"%s />"+"<br></p>"+
//                "<p>"+ supermarketName +"<br></p>"+
//                "<p>"+ productName +"</p>", imageUrl);

        String mailContent =String.format( "<p>"+ "<img src="+"%s />"+"<br></p>"+
                "<p>"+ supermarketName +"<br></p>"+
                "<p>"+ productName +"<br></p>"+
                "<P>" +successMessage+"</p>", imageUrl);

        System.out.println(mailContent);
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("osisiogubenjamin1@gmail.com",senderName);
        messageHelper.setTo(userEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent,true);
        javaMailSender.send(message);
    }
}
