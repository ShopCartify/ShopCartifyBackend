package com.shopcartify.mailSender;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
