package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class SupermarketNotFoundException extends ShopCartifyBaseException {
    public SupermarketNotFoundException() {
        super();
    }
    public SupermarketNotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
