package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.dto.reqests.CartResponse;

public interface CartService {
    CartResponse addToCart(UpdateCartRequest addToCartRequest);

    int getCartSize(String cartUniqueId);

    CartResponse removeFromCart(UpdateCartRequest addToCartRequest);
}
