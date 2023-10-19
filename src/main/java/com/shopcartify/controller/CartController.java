package com.shopcartify.controller;

import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.dto.responses.ShopCartifyApiResponse;
import com.shopcartify.exceptions.ShopCartifyBaseException;
import com.shopcartify.services.interfaces.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shopcartify.utils.ApiConstant.*;

@RestController
@RequestMapping(CART_CONTROLLER)
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(ADD_TO_CART)
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody UpdateCartRequest request){

        CartResponse response = cartService.addToCart(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(REMOVE_FROM_CART)
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestBody UpdateCartRequest request){
        CartResponse response = cartService.removeFromCart(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(FIND_CART_BY_UNIQUE_CART_ID)
   public ResponseEntity<?> findCartByUniqueCartId(@PathVariable String uniqueCartId) {
        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, cartService.findCartByUniqueCartId(uniqueCartId))
                    , HttpStatus.OK);
        }catch (ShopCartifyBaseException shopCartifyBaseException){
            return new ResponseEntity<>((new ShopCartifyApiResponse(false,shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }
   }
    @GetMapping(FIND_CART_PRODUCT_BY_UNIQUE_CART_ID)
    public ResponseEntity<?> findAllCartProductsByUniqueCartId(@PathVariable String uniqueCartId) {
        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, cartService.findAllCartProductsByUniqueCartId(uniqueCartId))
                    , HttpStatus.OK);
        }catch (ShopCartifyBaseException shopCartifyBaseException){
            return new ResponseEntity<>((new ShopCartifyApiResponse(false,shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }
    }
}
