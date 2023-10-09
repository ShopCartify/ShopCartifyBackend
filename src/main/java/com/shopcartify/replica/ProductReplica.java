package com.shopcartify.replica;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductReplica {
    private String productName;
    private String productImageUrl;
    private BigDecimal productPrice;
    private String productQrCodeUrl;
    private String productDescription;
    private String supermarketCode;
}
