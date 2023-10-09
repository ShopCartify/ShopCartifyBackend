package com.shopcartify.services.interfaces.adminServices;


import com.shopcartify.dto.reqests.AdminLoginRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.responses.AdminConfirmationResponse;
import com.shopcartify.dto.responses.AdminLoginResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Supermarket;

import java.util.List;

public interface SupermarketAdminService {

    SupermarketRegistrationResponse registerSupermarketAdmin(SupermarketRegistrationRequest superMarketAdminRegistrationRequest);
//     Supermarket findSupermarketAdminByEmail(String email);

//    List<ShopCartifyUser> findAllSupermarketAdminById(List<Long> id);


    AdminConfirmationResponse confirmNewSupermarketAdmin(String token);

    AdminLoginResponse loginSupermarketAdmin(AdminLoginRequest adminLoginRequest);

    Supermarket findSupermarketAdminByEmail(String email);

    List<ShopCartifyUser> findAllSupermarketAdminById(List<Long> id);
}
