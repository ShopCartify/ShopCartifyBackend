package com.shopcartify.controller;

import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.services.interfaces.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cartProduct")
@AllArgsConstructor
public class CartProductController {

    private final CartService cartService;

    @PostMapping("/addProduct")
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody UpdateCartRequest request){
        CartResponse response = cartService.addToCart(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestBody UpdateCartRequest request){
        CartResponse response = cartService.removeFromCart(request);
        return ResponseEntity.ok().body(response);
    }
}
