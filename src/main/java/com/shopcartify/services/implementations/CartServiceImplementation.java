package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.OrderRequest;
import com.shopcartify.dto.reqests.UpdateCartRequest;

import com.shopcartify.dto.responses.ConfirmOrderResponse;
import com.shopcartify.exceptions.CartNotFoundException;
import com.shopcartify.factory.GeneratorFactory;
import com.shopcartify.model.Cart;
import com.shopcartify.model.CartProduct;
import com.shopcartify.repositories.CartRepository;
import com.shopcartify.services.interfaces.CartProductService;
import com.shopcartify.services.interfaces.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;




@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartProductService cartProductService;
    private GeneratorFactory generatorFactory;
    @Override

    public CartResponse addToCart(UpdateCartRequest updateCartRequest) {
        log.info("At the start of the add to cart request method, " + updateCartRequest);
        Cart cart = new Cart();
        if( updateCartRequest.getUniqueCartId() == null ||updateCartRequest.getUniqueCartId().isEmpty() ){
            cart = generateCart();
        } else {


            cart = cartRepository.findByUniqueCartId(updateCartRequest.getUniqueCartId())
                    .orElse( generateCart());


            ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Africa/Lagos"));
            if(currentTime.isEqual(cart.getTimeCreated().plus(24, ChronoUnit.HOURS))){
                cartRepository.delete(cart);
                cart = generateCart();
            }
        }
        log.info("Right before the cart product is created : "+cart);
            updateCartRequest.setUniqueCartId(cart.getUniqueCartId());
            CartProduct cartProduct = cartProductService.createCartProduct(updateCartRequest);

        boolean itDoesNotExist = verifyCartProductDoesNotExist(cart, cartProduct);

        if (itDoesNotExist) {

            List<Long > cartProductIds = cart.getCartProductIds();

            cartProductIds.add(cartProduct.getId());


            cart.setCartProductIds(cartProductIds);

        }
        log.info("Cart Product has been created and does not exist in the cart ");

            Cart savedCart = cartRepository.save(cart);
//            cart.getCartProducts().add(cartProduct);



        CartResponse cartResponse = new CartResponse();

        cartResponse.setCartSize(savedCart.getCartProductIds().size());
        cartResponse.setUniqueCartId(cart.getUniqueCartId());

        return cartResponse;
    }

    private boolean verifyCartProductDoesNotExist(Cart cart, CartProduct cartProduct) {
        List<CartProduct> cartProducts = findAllCartProductsByUniqueCartId(cart.getUniqueCartId());
//        return cartProducts.contains(cartProduct);

        for (CartProduct productInCart : cartProducts) {
            boolean productIsFound = productInCart.getProductName() == cartProduct.getProductName() &&
                    productInCart.getSupermarketCode() == cartProduct.getSupermarketCode();
            if (productIsFound) return false;
        }
        return true;
    }

    private Cart generateCart() {
        Cart cart = new Cart();

//        cart.setCartProducts(new ArrayList<>());
        cart.setCartProductIds(new ArrayList<>());

       cart.setUniqueCartId(generateUniqueCartId());
        cart.setTimeCreated(ZonedDateTime.now(ZoneId.of("Africa/Lagos")));

        return cart;
    }

    private String generateUniqueCartId() {
        return generatorFactory.generateCode() + generatorFactory.generateCode();
    }

    @Override
    public int getCartSize(String cartUniqueId) {
        return cartRepository.findByUniqueCartId(cartUniqueId)
                .orElseThrow(()-> new CartNotFoundException("No such cart exists "))
//                .getCartProducts().size()
                .getCartProductIds().size();
    }



    @Override
    public CartResponse removeFromCart(UpdateCartRequest removeFromRequest) {
        Cart cart = cartRepository.findByUniqueCartId(removeFromRequest.getUniqueCartId())
                .orElseThrow(()-> new CartNotFoundException("Cart no longer exists or cart is empty"));



        List<Long> productsIdsInCart = cart.getCartProductIds();
        Cart savedCart = new Cart();

        for (int i = 0; i < productsIdsInCart.size(); i++) {

            CartProduct cartproduct = cartProductService.findById(productsIdsInCart.get(i));
            boolean isProduct =
                    cartproduct.getProductName().equals(removeFromRequest.getProductName())
                    && cartproduct.getSupermarketCode().equals(removeFromRequest.getSupermarketCode());
            if(isProduct){

                productsIdsInCart.remove(productsIdsInCart.get(i));
            }
        }
            savedCart = cartRepository.save(cart);

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartSize(savedCart.getCartProductIds().size());
        cartResponse.setUniqueCartId(cart.getUniqueCartId());
        return cartResponse;
    }

    @Override
    public Cart findCartByUniqueCartId(String uniqueCartId) {

         return cartRepository.findByUniqueCartId(uniqueCartId).orElseThrow(
                    ()-> new CartNotFoundException("Virtual cart does not exist. or your cart might be empty"));

    }
    @Override
    public List<CartProduct>  findAllCartProductsByUniqueCartId(String uniqueCartId) {

        Cart cart = findCartByUniqueCartId(uniqueCartId);

        List<Long> cartProductIds = cart.getCartProductIds();

        return cartProductService.findCartProductsByIds(cartProductIds);
    }
    @Override
    public ConfirmOrderResponse confirmOrder (OrderRequest orderRequest){

        return null;
    }

}