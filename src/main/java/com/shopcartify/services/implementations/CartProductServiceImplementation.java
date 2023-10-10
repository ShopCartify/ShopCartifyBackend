package com.shopcartify.services.implementations;


import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.model.CartProduct;
import com.shopcartify.services.interfaces.CartProductService;
import org.springframework.stereotype.Service;

@Service
public class CartProductServiceImplementation implements CartProductService {
    @Override
    public CartProduct createCartProduct(UpdateCartRequest updateCartRequest) {
        return null;
    }
}
