package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class FamilyNotFoundException extends ShopCartifyBaseException {
    public FamilyNotFoundException() {
    }
    public FamilyNotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
