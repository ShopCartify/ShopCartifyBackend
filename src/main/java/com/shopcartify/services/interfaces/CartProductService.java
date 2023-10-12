package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.model.CartProduct;

import java.util.List;

public interface CartProductService {
    CartProduct createCartProduct(UpdateCartRequest updateCartRequest);

    CartProduct findById(Long cartProductId);

    List<CartProduct> findCartProductsByIds(List<Long> cartProductIds);
}
