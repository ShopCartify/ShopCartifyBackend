package com.shopcartify.exceptions;

public class ShopCartifyBaseException extends RuntimeException{
    ShopCartifyBaseException(String message){
        super(message);
    }
    ShopCartifyBaseException(){
        super();
    }
}
