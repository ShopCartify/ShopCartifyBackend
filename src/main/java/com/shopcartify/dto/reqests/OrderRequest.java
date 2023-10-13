package com.shopcartify.dto.reqests;

import com.shopcartify.model.CartProduct;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<CartProduct> cartProducts;
}
