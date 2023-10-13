package com.shopcartify.mailSender;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(String to, String subject, String text);

    void sendEmailForRegistration(String to, String subject, String html) throws MessagingException, UnsupportedEncodingException;

    void sendEmailForNewProduct(String imageUrl, String productName, String supermarketName, String userEmail) throws MessagingException, UnsupportedEncodingException;

}
