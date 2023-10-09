package com.shopcartify.dto.responses;

import lombok.Data;

@Data
public class AdminConfirmationResponse {
    private String message;
    private String adminName;
    private String adminEmail;
    private String supermarketName;
    private String supermarketEmail;
    private String supermarketCode;
}
