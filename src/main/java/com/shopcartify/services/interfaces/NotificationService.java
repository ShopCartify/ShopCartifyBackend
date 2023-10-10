package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.NotificationRequest;
import com.shopcartify.model.ShopCartifyNotification;

public interface NotificationService {
public ShopCartifyNotification createNotification(NotificationRequest notificationRequest);
}
