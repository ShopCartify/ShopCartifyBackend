package com.shopcartify.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfProducts;

}
