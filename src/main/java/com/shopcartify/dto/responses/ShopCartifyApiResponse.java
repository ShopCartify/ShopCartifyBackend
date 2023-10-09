package com.shopcartify.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShopCartifyApiResponse {
    private boolean isSuccessFul;
    private Object data;

}
