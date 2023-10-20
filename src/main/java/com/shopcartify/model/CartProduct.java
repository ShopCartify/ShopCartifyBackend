package com.shopcartify.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int productQuantity;
    private String productName;
    private String productImageUrl;
    private BigDecimal productPrice;
    private String productDescription;
    private String supermarketCode;
    private String uniqueCartId;
    private Date date = new Date();

//    @JoinColumn(name = "cart_products_id")


}
