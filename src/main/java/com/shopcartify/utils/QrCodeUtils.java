package com.shopcartify.utils;


import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.model.Product;

public class QrCodeUtils {

    public static String createQrCodeText(Product product) {

        return  product.getSupermarketCode()+
                product.getProductName()+".";

    }
    public static String extractSupermarketCodeFrom(String token) {
        if (token ==null) throw new ProductNotFoundException("Product Not found");

        if (token.charAt(0) == '"')
           return token.substring(0, 6).substring(1);
        else return token.substring(0, 5);
    }


    public static String extractProductNameFrom(String token) {
        if (token.charAt(0) == '"')
            return token.substring(6, token.indexOf("."));
        else return token.substring(5, token.indexOf("."));
    }



}
