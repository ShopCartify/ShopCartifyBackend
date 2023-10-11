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

@Service
@AllArgsConstructor
public class CartProductServiceImplementation implements CartProductService {
    private ProductService productService;
    @Override
    public CartProduct createCartProduct(UpdateCartRequest updateCartRequest) {

        Product foundProduct = productService.findProductBySupermarketCodeAndProductName(updateCartRequest.getSupermarketCode(), updateCartRequest.getProductName());

        CartProduct cartProduct = new CartProduct();

        BeanUtils.copyProperties(foundProduct,cartProduct);
        cartProduct.setNumberOfProducts(updateCartRequest.getNumberOfProducts());

        return cartProduct;
    }
}
