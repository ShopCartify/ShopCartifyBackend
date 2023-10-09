package com.shopcartify.controller;

import com.shopcartify.model.Transaction;
import com.shopcartify.services.interfaces.adminServices.CheckoutAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/check-out")
@RequiredArgsConstructor
public class CheckOutAdminController {

    private final CheckoutAdmin checkoutAdmin;

//    @GetMapping("/transaction/{transactionId}")
//    public Optional<Transaction> viewTransactionById(@PathVariable Long transactionId) {
//        return checkoutAdmin.viewTransactionById(transactionId);
//    }

//    @GetMapping("/transactions")
//    public List<Transaction> viewAllTransactions() {
//        return checkoutAdmin.viewAllTransactions();
//    }
}
