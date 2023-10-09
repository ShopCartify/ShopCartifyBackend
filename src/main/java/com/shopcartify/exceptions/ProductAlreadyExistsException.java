package com.shopcartify.exceptions;

public class ProductAlreadyExistsException extends ShopCartifyBaseException {
    public ProductAlreadyExistsException( ) {}
    public ProductAlreadyExistsException(String  message) {
        super(message);
    }
}
