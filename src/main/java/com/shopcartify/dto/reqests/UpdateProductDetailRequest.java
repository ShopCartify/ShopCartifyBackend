package com.shopcartify.dto.reqests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDetailRequest {
    private BigDecimal productPrice;
    private String oldProductName;
    private String newProductName;
    private String description;
    private String imageUrl;
    private String supermarketCode;
}
