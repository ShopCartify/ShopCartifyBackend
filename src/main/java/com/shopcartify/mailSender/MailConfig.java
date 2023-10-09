package com.shopcartify.mailSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean(name = "customJavaMailSender")
    public JavaMailSender customJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        return mailSender;
    }
}
