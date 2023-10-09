//package com.shopcartify.services.implementations;
//
//import com.shopcartify.model.Receipt;
//import com.shopcartify.model.ReceiptItems;
//import com.shopcartify.model.ShopCartifyUser;
//import com.shopcartify.repositories.ReceiptRepository;
//import com.shopcartify.services.interfaces.ReceiptService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
////@DataJpaTest
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//class ReceiptServicesTest {
//
//    @MockBean
//    private ReceiptRepository receiptRepository;
//
//    private ReceiptService receiptService;
//
//    @BeforeEach
//    void setUp() {
//        receiptService = new ReceiptServices(receiptRepository);
//    }
//
//    @Test
//    void testCreateReceipt() {
//        ShopCartifyUser user = new ShopCartifyUser();
//        user.setUserId(1L);
//
//        List<ReceiptItems> items = new ArrayList<>();
//        items.add(new ReceiptItems("Item 1", BigDecimal.valueOf(10.0)));
//        items.add(new ReceiptItems("Item 2", BigDecimal.valueOf(20.0)));
//
//        BigDecimal totalAmount = BigDecimal.valueOf(30.0);
//
//        Receipt savedReceipt = new Receipt();
//        savedReceipt.setUser(user);
//        savedReceipt.setItems(items);
//        savedReceipt.setTotalAmount(totalAmount);
//
//        when(receiptRepository.save(savedReceipt)).thenReturn(savedReceipt);
//
//        Receipt createdReceipt = receiptService.createReceipt(user, items, totalAmount);
//
//        assertNotNull(createdReceipt);
//        assertEquals(user, createdReceipt.getUser());
//        assertEquals(items, createdReceipt.getItems());
//        assertEquals(totalAmount, createdReceipt.getTotalAmount());
//    }
//}
