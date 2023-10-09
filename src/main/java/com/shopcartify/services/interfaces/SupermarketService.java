package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.enums.UserRole;
import com.shopcartify.model.Admin;
import com.shopcartify.model.Product;
import com.shopcartify.model.Supermarket;

import java.util.List;

public interface SupermarketService {
    SupermarketRegistrationResponse registerNewSupermarket(SupermarketRegistrationRequest supermarketRegistrationRequest);

    NewProductResponse addNewProduct(NewProductRequest productRequest);

//    Product findProductByToken(String token);

    Supermarket findSupermarketBySupermarketCode(String supermarketCode);

//    Product findProductByNameAndSupermarketCode(String productName, String supermarketCode);

    Product findProductByToken(String token);

    Product findProductByNameAndSupermarketCode(String productName);

    Supermarket findProductById(Long id);
    Supermarket findBySuperMarketCode(String code);
    List<Supermarket> findAllSupermarket(List<Long> id);

    Supermarket update(Supermarket supermarket);

    Admin inviteAdminToSupermarket(String supermarketCode, String adminEmail, UserRole adminRole);

}
