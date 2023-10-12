package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.model.CartProduct;

public interface CartProductService {
    CartProduct createCartProduct(UpdateCartRequest updateCartRequest);

    CartProduct findById(Long cartProductId);
}
