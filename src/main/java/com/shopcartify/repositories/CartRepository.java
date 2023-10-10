package com.shopcartify.repositories;

import com.shopcartify.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUniqueCartId(String uniqueCartId);
}
