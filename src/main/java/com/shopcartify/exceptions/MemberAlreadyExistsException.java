package com.shopcartify.exceptions;

import com.shopcartify.enums.ExceptionMessage;

public class MemberAlreadyExistsException extends ShopCartifyBaseException{
    public MemberAlreadyExistsException() {
        super();
    }
    public MemberAlreadyExistsException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
