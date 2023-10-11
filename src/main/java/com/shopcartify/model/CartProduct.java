package com.shopcartify.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfProducts;
    private String productName;
    private String productImageUrl;
    private BigDecimal productPrice;
    private String productDescription;
    private String supermarketCode;

}
