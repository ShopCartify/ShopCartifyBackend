package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.model.Cart;
import com.shopcartify.model.CartProduct;
import com.shopcartify.repositories.CartRepository;
import com.shopcartify.services.interfaces.CartService;
import com.shopcartify.services.interfaces.CartProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    CartRepository cartRepository;
    CartProductService cartProductService;
    @Override
    public CartResponse addToCart(UpdateCartRequest updateCartRequest) {

        Cart cart = cartRepository.findByUniqueCartId(updateCartRequest.getCartUniqueId())
                .orElse(generateCart());

        CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);

        cart.getCartProducts().add(cartProduct);

        Cart savedCart = cartRepository.save(cart);

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartSize(savedCart.getCartProducts().size());
        return cartResponse;
    }

    private Cart generateCart() {
        Cart cart = new Cart();

        return null;
    }

    @Override
    public int getCartSize(String cartUniqueId) {
        return 0;
    }

    @Override
    public CartResponse removeFromCart(UpdateCartRequest addToCartRequest) {
        return null;
    }
}
