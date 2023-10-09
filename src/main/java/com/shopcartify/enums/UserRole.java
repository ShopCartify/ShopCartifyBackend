package com.shopcartify.enums;

public enum UserRole {
    CUSTOMER,
    ADMIN,
    SUPER_ADMIN,
    SUPERMARKET_ADMIN,
    CHECKOUT_ADMIN,

    FAMILY_ACCOUNT;

    public boolean contains(UserRole userRole) {
        return userRole.contains(UserRole.CHECKOUT_ADMIN);
    }

    public void add(UserRole adminRole) {
    }
}
