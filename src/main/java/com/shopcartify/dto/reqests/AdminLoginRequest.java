package com.shopcartify.dto.reqests;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String adminEmail;
    private String adminPassword;
    private String supermarketCode;
}
