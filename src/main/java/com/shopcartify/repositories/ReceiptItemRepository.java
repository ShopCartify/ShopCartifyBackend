package com.shopcartify.repositories;

import com.shopcartify.model.ReceiptItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItems, Long> {
}
