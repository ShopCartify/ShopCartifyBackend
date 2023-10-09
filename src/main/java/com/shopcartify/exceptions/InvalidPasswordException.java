package com.shopcartify.exceptions;

public class InvalidPasswordException extends ShopCartifyBaseException {
    public InvalidPasswordException(String message) {
        super(message);
    }
    public InvalidPasswordException() {
        super();
    }
}
