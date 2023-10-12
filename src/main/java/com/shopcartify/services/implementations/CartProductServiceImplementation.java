package com.shopcartify.services.implementations;


import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.model.CartProduct;
import com.shopcartify.model.Product;
import com.shopcartify.repositories.CartProductRepository;
import com.shopcartify.repositories.ProductRepository;
import com.shopcartify.services.interfaces.CartProductService;
import com.shopcartify.services.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartProductServiceImplementation implements CartProductService {
    private ProductService productService;
    private CartProductRepository cartProductRepository;
    @Override
    public CartProduct createCartProduct(UpdateCartRequest updateCartRequest) {

        Product foundProduct = productService.findProductBySupermarketCodeAndProductName(updateCartRequest.getSupermarketCode(), updateCartRequest.getProductName());

        CartProduct cartProduct = new CartProduct();

        BeanUtils.copyProperties(foundProduct,cartProduct);
        cartProduct.setNumberOfProducts(updateCartRequest.getNumberOfProducts());
        cartProduct.setUniqueCartId(updateCartRequest.getUniqueCartId());

        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProduct findById(Long cartProductId) {
        return cartProductRepository.findById(cartProductId)
                .orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    @Override
    public List<CartProduct> findCartProductsByIds(List<Long> cartProductIds) {
        List<CartProduct> cartProducts = new ArrayList<>();
        for (Long cartProductId : cartProductIds) {
            cartProducts.add(cartProductRepository.findById(cartProductId).orElse(null));
            }
        return cartProducts;
    }
}
