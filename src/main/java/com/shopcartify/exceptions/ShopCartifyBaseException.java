package com.shopcartify.exceptions;

public class ShopCartifyBaseException extends RuntimeException{
    public ShopCartifyBaseException(String message){
        super(message);
    }
    ShopCartifyBaseException(){
        super();
    }
}
