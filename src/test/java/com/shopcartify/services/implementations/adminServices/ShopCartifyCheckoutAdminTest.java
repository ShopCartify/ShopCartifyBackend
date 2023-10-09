package com.shopcartify.services.implementations.adminServices;

import com.shopcartify.model.Transaction;
import com.shopcartify.repositories.TransactionRepository;
import com.shopcartify.services.interfaces.adminServices.CheckoutAdmin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Transactional
class ShopCartifyCheckoutAdminTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Autowired
    private CheckoutAdmin checkoutAdmin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionAmount(BigDecimal.valueOf(50.0));

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

//        Optional<Transaction> result = checkoutAdmin.viewTransactionById(1L);
//        assertEquals(Optional.of(transaction), result);
    }

    @Test
    public void testViewAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setTransactionAmount(BigDecimal.valueOf(50.0));
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setTransactionAmount(BigDecimal.valueOf(75.0));
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

//        List<Transaction> result = checkoutAdmin.viewAllTransactions();

//        assertEquals(transactions, result);
    }

}