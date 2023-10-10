package com.shopcartify.repositories;
import com.shopcartify.model.ShopCartifyNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<ShopCartifyNotification, Long> {
}
