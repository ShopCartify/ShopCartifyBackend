package com.shopcartify.model;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String uniqueCartId;

//    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id")
//    private List<CartProduct> cartProducts;
    @ElementCollection(fetch =  FetchType.EAGER)
    private List<Long> cartProductIds ;

    private ZonedDateTime timeCreated;
//    @OneToOne
//    private CartProduct cartProducts;


    public List<Long> getCartProductIds() {
        return cartProductIds;
    }

    public void setCartProductIds(List<Long> cartProductIds) {
        this.cartProductIds = cartProductIds;
    }
}
