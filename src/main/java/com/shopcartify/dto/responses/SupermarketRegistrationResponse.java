package com.shopcartify.dto.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SupermarketRegistrationResponse {
    private String supermarketName;
    private String supermarketEmail;
    private String supermarketCode;
    private String message;
    private String emailOfRegisteredUser;
    private String emailUrl;
}
