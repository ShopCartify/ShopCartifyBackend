package com.shopcartify.utils;

public class ApiConstant {

    //CART CONTROLLER
    public static final String CART_CONTROLLER = "/api/v1/cart";
    public static final String ADD_TO_CART = "/addToCart";
    public static final String REMOVE_FROM_CART = "/removeFromCart";
    public static final String FIND_CART_BY_UNIQUE_CART_ID = "/findCartByUniqueCartId/{uniqueCartId}";
    public static final String FIND_CART_PRODUCT_BY_UNIQUE_CART_ID = "/findAllCartProductsByUniqueCartId/{uniqueCartId}";

    //PRODUCT CONTROLLER
    public static final String PRODUCT_CONTROLLER= "api/v1/productController";
    public static final String FIND_PRODUCT_BY_TOKEN= "/findProductByToken/{token}";

    // SUPERMARKET_ADMIN_CONTROLLER
    public static final String SUPERMARKET_ADMIN_CONTROLLER= "api/v1/supermarketAdminController";
    public static final String REGISTER_SUPERMARKET_ADMIN= "/registerSupermarketAdmin";
    public static final String CONFIRM_SUPERMARKET_ADMIN= "/confirmNewSupermarketAdmin/{token}";
    public static final String ADD_NEW_PRODUCT= "/addNewProduct";
    public static final String UPDATE_PRODUCT_DETAIL= "/updateProductDetail";
    public static final String FIND_ALL_PRODUCTS_WITH_PAGINATION_AND_SORTING_WITH_DIRECTION
            = "/findAllProductsWithPaginationAndSortingWithDirection";

    //USER CONTROLLER
    public static final String USER_CONTROLLER = "api/v1/user";
    public static final String FIND_USER_BY_ID = "/find-by-id/{userId}";
    public static final String FIND_USER_BY_EMAIL = "/find-by-user-email";
    public static final String GET_ALL_USERS = "/get-all-users";
    public static final String FIND_ALL_USERS = "/find-all-users";
    public static final String FIND_USER_BY_USER_NAME = "/find-by-username";
    public static final String UPDATE_PROFILE_BY_EMAIL = "/update-profile";
    public static final String CHANGE_PASSWORD_BY_ID = "/{userId}/change-password";
    public static final String MAKE_PAYMENT = "/make-payment";


}
