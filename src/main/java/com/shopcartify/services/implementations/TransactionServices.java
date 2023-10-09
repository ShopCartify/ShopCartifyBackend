package com.shopcartify.services.implementations;

import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Transaction;
import com.shopcartify.repositories.TransactionRepository;
import com.shopcartify.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServices implements TransactionService {
    private final TransactionRepository transactionRepository;

//    @Override
//    public List<Transaction> getTransactionHistoryForUser(ShopCartifyUser user) {
//        return transactionRepository.findByUser(user);
//    }
//
//    @Override
//    public List<Transaction> getAllTransactions() {
//        return transactionRepository.findAll();
//    }
}
