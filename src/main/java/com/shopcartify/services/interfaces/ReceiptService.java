package com.shopcartify.services.interfaces;

import com.shopcartify.model.Receipt;
import com.shopcartify.model.ReceiptItems;
import com.shopcartify.model.ShopCartifyUser;

import java.math.BigDecimal;
import java.util.List;

public interface ReceiptService {
    Receipt createReceipt(ShopCartifyUser user, List<ReceiptItems> items, BigDecimal totalAmount);
}
