package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.model.Cart;
import com.shopcartify.model.CartProduct;

import java.util.List;

public interface CartService {
    CartResponse addToCart(UpdateCartRequest addToCartRequest);

    int getCartSize(String cartUniqueId);

    CartResponse removeFromCart(UpdateCartRequest addToCartRequest);
    Cart findCartByUniqueCartId(String uniqueCartId);

    List<CartProduct> findAllCartProductsByUniqueCartId(String uniqueCartId);
}
