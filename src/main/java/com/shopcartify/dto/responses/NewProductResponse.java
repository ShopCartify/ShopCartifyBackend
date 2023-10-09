package com.shopcartify.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewProductResponse {
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private BigDecimal productPrice;
    private String productQrCodeUrl;
    private String message;
}
