//package com.shopcartify.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Data
//@Builder
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "Family")
//public class Family {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String familyName;
//
////    @ManyToMany
////    @JoinTable(
////            name = "family_members",
////            joinColumns = @JoinColumn(name = "family_id"),
////            inverseJoinColumns = @JoinColumn(name = "user_id")
////    )
////    private Set<ShopCartifyUser> members = new HashSet<>();
////
////}
