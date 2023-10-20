//package com.shopcartify.services.implementations;
//
//import com.shopcartify.model.Product;
//import com.shopcartify.model.ShopCartifyUser;
//import com.shopcartify.model.WishList;
//import com.shopcartify.repositories.WishlistRepository;
//import com.shopcartify.services.interfaces.WishlistService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class WishListServices implements WishlistService {
////    private final WishlistRepository wishlistRepository;
////
////
////    @Override
////    public WishList addToWishlist(ShopCartifyUser user, Optional<Product> product) {
////        WishList wishlist = wishlistRepository.findByCustomer(user);
////        if (wishlist == null) {
////            wishlist = new WishList();
////            wishlist.setCustomer(user);
////        }
////        wishlist.getItems().add(product.get());
////        return wishlistRepository.save(wishlist);
////    }
////
////    @Override
////    public WishList removeFromWishlist(ShopCartifyUser user, Product product) {
////        WishList wishlist = wishlistRepository.findByCustomer(user);
////        if (wishlist != null) {
////            wishlist.getItems().remove(product);
////            return wishlistRepository.save(wishlist);
////        }
////        return null;
////    }
//}
