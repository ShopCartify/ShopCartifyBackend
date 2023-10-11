package com.shopcartify.exceptions;

public class CartNotFoundException extends ShopCartifyBaseException {
    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException( ) {

    }
}
