package com.shopcartify.services.interfaces.adminServices;


import com.shopcartify.enums.UserRole;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Supermarket;

import java.util.List;

public interface SuperAdminService {
    Supermarket findSuperAdminByEmail(String email);
    List<ShopCartifyUser> findAllSuperAdminById(List<Long> id);

    ShopCartifyUser inviteAdmin(String email, UserRole role);
}

