package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class UserAlreadyExistsException extends ShopCartifyBaseException{
    public UserAlreadyExistsException() {
        super();
    }
    public UserAlreadyExistsException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
