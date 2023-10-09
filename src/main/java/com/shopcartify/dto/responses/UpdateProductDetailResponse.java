package com.shopcartify.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDetailResponse {
    private BigDecimal ProductPrice;
    private String productName;
    private String supermarketCode;
    private String message;
}
