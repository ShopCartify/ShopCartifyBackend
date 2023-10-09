package com.shopcartify.repositories;

import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(ShopCartifyUser user);
}
