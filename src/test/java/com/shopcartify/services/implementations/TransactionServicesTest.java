//package com.shopcartify.services.implementations;
//
//import com.shopcartify.model.ShopCartifyUser;
//import com.shopcartify.model.Transaction;
//import com.shopcartify.repositories.TransactionRepository;
//import com.shopcartify.services.interfaces.TransactionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//class TransactionServicesTest {
//    @MockBean
//    private TransactionRepository transactionRepository;
//
//    private TransactionService transactionService;
//
//    @BeforeEach
//    void setUp() {
//        transactionService = new TransactionServices(transactionRepository);
//    }
//
//    @Test
//    void testGetTransactionHistoryForUser() {
//        ShopCartifyUser user = new ShopCartifyUser();
//        user.setUserId(1L);
//
//        List<Transaction> userTransactions = new ArrayList<>();
//        userTransactions.add(new Transaction(1L, user, "Purchase 1"));
//        userTransactions.add(new Transaction(2L, user, "Purchase 2"));
//
//        when(transactionRepository.findByUser(user)).thenReturn(userTransactions);
//
//        List<Transaction> result = transactionService.getTransactionHistoryForUser(user);
//
//        assertEquals(userTransactions.size(), result.size());
//        assertEquals(userTransactions, result);
//    }
//
//    @Test
//    void testGetAllTransactions() {
//        List<Transaction> allTransactions = new ArrayList<>();
//        allTransactions.add(new Transaction(1L, new ShopCartifyUser(), "Purchase 1"));
//        allTransactions.add(new Transaction(2L, new ShopCartifyUser(), "Purchase 2"));
//
//        when(transactionRepository.findAll()).thenReturn(allTransactions);
//
//        List<Transaction> result = transactionService.getAllTransactions();
//
//        assertEquals(allTransactions.size(), result.size());
//        assertEquals(allTransactions, result);
//    }
//
//}