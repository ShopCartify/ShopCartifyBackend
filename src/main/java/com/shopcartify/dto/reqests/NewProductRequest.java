package com.shopcartify.dto.reqests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductRequest {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImageUrl;
    private String productQrCodeUrl;
    private String supermarketName;
    private String supermarketCode;
    private String supermarketAdminEmail;
}
