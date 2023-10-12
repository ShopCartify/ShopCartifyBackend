package com.shopcartify.dto.reqests;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private String productName;
    private String supermarketCode;
    private String uniqueCartId;
    private int numberOfProducts;
}
