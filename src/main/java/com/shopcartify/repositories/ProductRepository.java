package com.shopcartify.repositories;

import com.shopcartify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductBySupermarketCodeAndProductName(String productName, String name);

    boolean existsBySupermarketCodeAndProductName(String supermarketCode, String productName);
}
