package com.shopcartify.repositories;

import com.shopcartify.model.ShopCartifyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ShopCartifyUser, Long> {
    Optional<ShopCartifyUser> findByEmail(String email);

    Optional<ShopCartifyUser> findByUserName(String username);

}
