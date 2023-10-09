package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class OwnerNotFoundException extends ShopCartifyBaseException {
    public OwnerNotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
    public OwnerNotFoundException() {
    }
}
