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

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody UpdateCartRequest request){

        CartResponse response = cartService.addToCart(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestBody UpdateCartRequest request){
        CartResponse response = cartService.removeFromCart(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findCartByUniqueCartId/{uniqueCartId}")
   public ResponseEntity<?> findCartByUniqueCartId(@PathVariable String uniqueCartId) {
        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, cartService.findCartByUniqueCartId(uniqueCartId))
                    , HttpStatus.OK);
        }catch (ShopCartifyBaseException shopCartifyBaseException){
            return new ResponseEntity<>((new ShopCartifyApiResponse(false,shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }
   }
}
