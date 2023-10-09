package com.shopcartify.exceptions;
import com.shopcartify.enums.ExceptionMessage;

public class MemberNotFoundException extends ShopCartifyBaseException {
    public MemberNotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
    public MemberNotFoundException() {

    }
}
