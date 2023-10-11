package com.shopcartify.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
     private String uniqueCartId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts;

    private ZonedDateTime timeCreated;
//    @OneToOne
//    private CartProduct cartProducts;



}
