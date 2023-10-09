//package com.shopcartify.services.implementations;
//
//import com.shopcartify.model.Product;
//import com.shopcartify.model.ShopCartifyUser;
//import com.shopcartify.model.WishList;
//import com.shopcartify.repositories.WishlistRepository;
//import com.shopcartify.services.interfaces.WishlistService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//class WishListServicesTest {
//    @MockBean
//    private WishlistRepository wishlistRepository;
//
//    private WishlistService wishlistService;
//
//    @BeforeEach
//    void setUp() {
//        wishlistService =
////                new WishListServices(wishlistRepository);
//    }
//
//    @Test
//    void testAddToWishlist() {
//        ShopCartifyUser user = new ShopCartifyUser();
//        user.setUserId(1L);
//
//        Product product = new Product();
//        product.setId(1L);
//
//        WishList existingWishlist = new WishList();
//        existingWishlist.setCustomer(user);
//        Set<Product> items = new HashSet<>();
//        items.add(product);
//        existingWishlist.setItems(items);
//
//        when(wishlistRepository.findByCustomer(user)).thenReturn(existingWishlist);
//        when(wishlistRepository.save(existingWishlist)).thenReturn(existingWishlist);
//
//        WishList updatedWishlist = wishlistService.addToWishlist(user, Optional.of(product));
//
//        assertNotNull(updatedWishlist);
//        assertEquals(existingWishlist, updatedWishlist);
//        assertTrue(updatedWishlist.getItems().contains(product));
//    }
//
//    @Test
//    void testRemoveFromWishlist() {
//        ShopCartifyUser user = new ShopCartifyUser();
//        user.setUserId(1L);
//
//        Product product = new Product();
//        product.setId(1L);
//
//        WishList existingWishlist = new WishList();
//        existingWishlist.setCustomer(user);
//        Set<Product> items = new HashSet<>();
//        items.add(product);
//        existingWishlist.setItems(items);
//
//        when(wishlistRepository.findByCustomer(user)).thenReturn(existingWishlist);
//        when(wishlistRepository.save(existingWishlist)).thenReturn(existingWishlist);
//
//        WishList updatedWishlist = wishlistService.removeFromWishlist(user, product);
//
//        assertNotNull(updatedWishlist);
//        assertEquals(existingWishlist, updatedWishlist);
//        assertFalse(updatedWishlist.getItems().contains(product));
//    }
//
//}