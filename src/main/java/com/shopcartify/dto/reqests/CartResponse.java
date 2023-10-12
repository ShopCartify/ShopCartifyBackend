package com.shopcartify.dto.reqests;

import lombok.Data;

@Data
public class CartResponse {
    private int cartSize;
    private String uniqueCartId;
}
