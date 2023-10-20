//package com.shopcartify.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class WishList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private ShopCartifyUser customer;
//
//    @ManyToMany
//    @JoinTable(
//            name = "wishlist_items",
//            joinColumns = @JoinColumn(name = "wishlist_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private Set<Product> items = new HashSet<>();
//
//}
