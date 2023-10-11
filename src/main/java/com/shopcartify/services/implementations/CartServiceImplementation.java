package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.UpdateCartRequest;

import com.shopcartify.exceptions.CartNotFoundException;
import com.shopcartify.model.Cart;
import com.shopcartify.model.CartProduct;
import com.shopcartify.repositories.CartRepository;
import com.shopcartify.services.interfaces.CartProductService;
import com.shopcartify.services.interfaces.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;




@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    CartRepository cartRepository;
    CartProductService cartProductService;
        @Override
    public CartResponse addToCart(UpdateCartRequest updateCartRequest) {
            Cart savedCart = new Cart();

            if (updateCartRequest.getCartUniqueId() == null || updateCartRequest.getCartUniqueId().isEmpty()) {
                savedCart = generateCart(updateCartRequest);
                savedCart = cartRepository.save(savedCart);
            } else {
                Cart cart = cartRepository.findByUniqueCartId(updateCartRequest.getCartUniqueId())
                        .orElseThrow(() -> new CartNotFoundException("Cart not found"));

                ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Africa/Lagos"));
                if (currentTime.isEqual(cart.getTimeCreated().plus(24, ChronoUnit.HOURS))) {
                    cartRepository.delete(cart);
                    savedCart = generateCart(updateCartRequest);
                    savedCart = cartRepository.save(savedCart);
                }

                CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);
//            List<CartProduct> cartProductList = new ArrayList<>();
                List<CartProduct> cartProductList = cart.getCartProducts();
                cartProductList.add(cartProduct);
                cart.setCartProducts(cartProductList);

//            savedCart = cartRepository.save(cart);
            }
        }

    private Cart generateCart(UpdateCartRequest updateCartRequest) {
        Cart cart = new Cart();
        cart.setCartProducts(new ArrayList<>());
        CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);
        cart.getCartProducts().add(cartProduct);
        cart.setTimeCreated(ZonedDateTime.now(ZoneId.of("Africa/Lagos")));
        return cartRepository.save(cart);
    }

    @Override
    public int getCartSize(String cartUniqueId) {
        return 0;
    }



    @Override
    public CartResponse removeFromCart(UpdateCartRequest removeFromRequest) {
        Cart cart = cartRepository.findByUniqueCartId(removeFromRequest.getCartUniqueId())
                .orElseThrow(()-> new CartNotFoundException("Cart not found"));

        List<CartProduct> productsInCart = cart.getCartProducts();
        Cart savedCart = new Cart();
        for (int i = 0; i < productsInCart.size(); i++) {
            boolean isProduct = productsInCart.get(i).getProductName().equals(removeFromRequest.getProductName())
                    && productsInCart.get(i).getSupermarketCode().equals(removeFromRequest.getSupermarketCode());
            if(isProduct){
                productsInCart.remove(productsInCart.get(i));
            }
            savedCart = cartRepository.save(cart);
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartSize(savedCart.getCartProducts().size());
        return cartResponse;
    }

    @Override
    public Cart findCartByUniqueCartId(String uniqueCartId) {
        System.out.println(uniqueCartId);
        return cartRepository.findByUniqueCartId(uniqueCartId).orElseThrow(
                    ()-> new CartNotFoundException("Virtual cart does not exist. or your cart might be empty"));
    }
}
//package com.shopcartify.services.implementations;
//
//import com.shopcartify.dto.reqests.CartResponse;
//import com.shopcartify.dto.reqests.UpdateCartRequest;
//
//import com.shopcartify.exceptions.CartNotFoundException;
//import com.shopcartify.model.Cart;
//import com.shopcartify.model.CartProduct;
//import com.shopcartify.repositories.CartRepository;
//import com.shopcartify.services.interfaces.CartProductService;
//import com.shopcartify.services.interfaces.CartService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//
//@Service
//@AllArgsConstructor
//public class CartServiceImplementation implements CartService {
//
//    CartRepository cartRepository;
//    CartProductService cartProductService;
//    @Override
//    public CartResponse addToCart(UpdateCartRequest updateCartRequest) {
//        Cart savedCart = new Cart();
//
//        if(updateCartRequest.getCartUniqueId() == null || updateCartRequest.getCartUniqueId().isEmpty()){
//            savedCart = generateCart(updateCartRequest);
//            savedCart = cartRepository.save(savedCart);
//        } else {
//            Cart cart = cartRepository.findByUniqueCartId(updateCartRequest.getCartUniqueId())
//                    .orElseThrow(()-> new CartNotFoundException("Cart not found"));
//
//            ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Africa/Lagos"));
//            if(currentTime.isEqual(cart.getTimeCreated().plus(24, ChronoUnit.HOURS))){
//                cartRepository.delete(cart);
//                savedCart = generateCart(updateCartRequest);
//                savedCart = cartRepository.save(savedCart);
//            }
//
//            CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);
////            List<CartProduct> cartProductList = new ArrayList<>();
//            List<CartProduct> cartProductList = cart.getCartProducts();
//            cartProductList.add(cartProduct);
//            cart.setCartProducts(cartProductList);
//
////            savedCart = cartRepository.save(cart);
//        }
//
//        CartResponse cartResponse = new CartResponse();
//        cartResponse.setCartSize(savedCart.getCartProducts().size());
//        return cartResponse;
//    }
//
//    private Cart generateCart(UpdateCartRequest updateCartRequest) {
//        Cart cart = new Cart();
//        CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);
//
//        List<CartProduct> cartProductList = new ArrayList<>();
//        cartProductList.add(cartProduct);
//        cart.setCartProducts(cartProductList);
//
//        cart.setTimeCreated(ZonedDateTime.now(ZoneId.of("Africa/Lagos")));
//        return cartRepository.save(cart);
////        return cart;
//    }
//
//    @Override
//    public int getCartSize(String cartUniqueId) {
//        return 0;
//    }
//
//    @Override
//    public CartResponse removeFromCart(UpdateCartRequest removeFromRequest) {
//        Cart cart = cartRepository.findByUniqueCartId(removeFromRequest.getCartUniqueId())
//                .orElseThrow(()-> new CartNotFoundException("Cart not found"));
//
//        List<CartProduct> productsInCart = cart.getCartProducts();
//        Cart savedCart = new Cart();
//        for (int i = 0; i < productsInCart.size(); i++) {
//            boolean isProduct = productsInCart.get(i).getProductName().equals(removeFromRequest.getProductName())
//                    && productsInCart.get(i).getSupermarketCode().equals(removeFromRequest.getSupermarketCode());
//            if(isProduct){
//                productsInCart.remove(productsInCart.get(i));
//            }
//            savedCart = cartRepository.save(cart);
//        }
//
//        CartResponse cartResponse = new CartResponse();
//        cartResponse.setCartSize(savedCart.getCartProducts().size());
//        return cartResponse;
//    }
//
//    @Override
//    public Cart findCartByUniqueCartId(String uniqueCartId) {
//        System.out.println(uniqueCartId);
//        return cartRepository.findByUniqueCartId(uniqueCartId).orElseThrow(
//                    ()-> new CartNotFoundException("Virtual cart does not exist. or your cart might be empty"));
//    }
//}