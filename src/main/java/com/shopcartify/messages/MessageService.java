package com.shopcartify.messages;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String getRegistrationSuccessMessage() {
        return "Registration successful! Please, check your email to complete your registration";
    }

    public String getAccountAlreadyVerifiedMessage() {
        return "This account has already been verified, Please Login";
    }

    public String getEmailVerificationSuccessMessage() {
        return "Email verification successful";
    }

    public String getInvalidEmailVerificationMessage() {
        return "Invalid verification token";
    }
    public String getUserAlreadyExistsMessage(String email) {
        return "User with email: " + email + " already exists";
    }

    public String getInvalidVerificationTokenMessage() {
        return "Invalid verification token";
    }

    public String getTokenExpiredMessage() {
        return "Token already expired";
    }
}
