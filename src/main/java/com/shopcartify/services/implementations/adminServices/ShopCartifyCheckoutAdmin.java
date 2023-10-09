package com.shopcartify.services.implementations.adminServices;

import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Supermarket;
import com.shopcartify.model.Transaction;
import com.shopcartify.repositories.SupermarketRepository;
import com.shopcartify.repositories.TransactionRepository;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.services.interfaces.adminServices.CheckoutAdmin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shopcartify.enums.UserRole.CHECKOUT_ADMIN;


@Service
@AllArgsConstructor

public class ShopCartifyCheckoutAdmin implements CheckoutAdmin {

//    private final UserRepository userRepository;
//    private final SupermarketRepository supermarketRepository;
//    private final TransactionRepository transactionRepository;
//    @Override
//    public Supermarket findSuperAdminByEmail(String email) {
////        var foundSuperAdmin = supermarketRepository.findAllSuperAdmin();
//        return null;
        
//    }
//    @Override
//    public List<ShopCartifyUser> findAllCheckoutAdmin() {
//        List<ShopCartifyUser> allAdmins = userRepository.findAll();
//        List<ShopCartifyUser> checkoutAdmins = new ArrayList<>();
//
//        for (int i = 0; i < allAdmins.size(); i++) {
//            if(allAdmins.get(i).getRoles().contains(CHECKOUT_ADMIN)){
//                checkoutAdmins.add(allAdmins.get(i));
//            }
//        }
//        return checkoutAdmins;
//    }

//    @Override
//    public Optional<Transaction> viewTransactionById(Long transactionId) {
//        return transactionRepository.findById(transactionId);
//    }

//    @Override
//    public List<Transaction> viewAllTransactions() {
//        return transactionRepository.findAll();
//    }

}
