package com.shopcartify.services.implementations;

import com.shopcartify.model.Receipt;
import com.shopcartify.model.ReceiptItems;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.repositories.ReceiptRepository;
import com.shopcartify.services.interfaces.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptServices implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Override
    public Receipt createReceipt(ShopCartifyUser user, List<ReceiptItems> items, BigDecimal totalAmount) {
        Receipt receipt = new Receipt();
        receipt.setUser(user);
        receipt.setItems(items);
        receipt.setTotalAmount(totalAmount);
        return receiptRepository.save(receipt);
    }
}
