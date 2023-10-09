package com.shopcartify.repositories;

import com.shopcartify.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    Optional<Supermarket> findBySupermarketCode(String supermarketCode);

    Optional<Supermarket> findSupermarketBySupermarketName(String supermarketName);




}
