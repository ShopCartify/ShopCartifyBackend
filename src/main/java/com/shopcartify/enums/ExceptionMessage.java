package com.shopcartify.enums;

public enum ExceptionMessage {

    USER_ALREADY_EXISTS("User with email %s already exists"),
    USER_NOT_FOUND_EXCEPTION("User not found"),
    USER_WITH_ID_NOT_FOUND_EXCEPTION("User not found with ID"),
    USER_WITH_EMAIL_NOT_FOUND_EXCEPTION("User with email %s not found"),
    USER_REGISTRATION_FAILED_EXCEPTION("User registration failed"),
    ACCOUNT_ACTIVATION_FAILED_EXCEPTION("Account activation was not successful"),
    INVALID_CREDENTIALS_EXCEPTION("Invalid authentication credentials"),
    AUTHENTICATION_NOT_SUPPORTED("Authentication not supported on this system"),

    MEMBER_ALREADY_EXIST_EXCEPTION("User is already a member of the family account"),

    INVALID_PASSWORD_EXCEPTION("Current password is incorrect"),

    FAMILY_ALREADY_EXIST_EXCEPTION("Family with this name already exist"),
    PRODUCT_NOT_FOUND(" Product not found"),
    PRODUCT_ALREADY_EXIST(" Product already exists"),
    PRODUCT_ADDED_SUCCESSFULLY("Product added successfully"),
    SUPERMARKET_NOT_FOUND("SupermarketNotFound");

//    FAMILY_NOT_FOUND_EXCEPTION("Family not found"),
//    USER_WITH_USERNAME_NOT_FOUND_EXCEPTION("User with username not found"),
//
//    OWNER_NOT_FOUND_EXCEPTION("Owner not found with ID %s"),
//
//
//    MEMBER_NOT_FOUND_EXCEPTION("Member not found with username %s");

    ExceptionMessage(String message){
        this.message = message;
    }
    private final String message;

    public String getMessage(){
        return message;
    }
}
