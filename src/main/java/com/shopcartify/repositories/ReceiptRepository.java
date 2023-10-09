package com.shopcartify.repositories;

import com.shopcartify.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Optional<Object> findById(UUID id);
}
