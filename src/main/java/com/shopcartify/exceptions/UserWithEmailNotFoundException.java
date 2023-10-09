package com.shopcartify.exceptions;

public class UserWithEmailNotFoundException extends ShopCartifyBaseException {
    public UserWithEmailNotFoundException(String message) {
        super(message);
    }
}
