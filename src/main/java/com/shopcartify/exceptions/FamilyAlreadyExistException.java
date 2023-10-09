package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class FamilyAlreadyExistException extends ShopCartifyBaseException{
    public FamilyAlreadyExistException(ExceptionMessage message) {
        super(message.getMessage());
    }
    public FamilyAlreadyExistException() {
        super();
    }
}
