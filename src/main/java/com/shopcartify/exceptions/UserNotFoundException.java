package com.shopcartify.exceptions;


import com.shopcartify.enums.ExceptionMessage;

public class UserNotFoundException extends ShopCartifyBaseException{
    public UserNotFoundException( ) {
        super();
    }
    public UserNotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
