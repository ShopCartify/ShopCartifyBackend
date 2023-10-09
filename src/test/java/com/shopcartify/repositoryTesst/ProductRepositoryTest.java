package com.shopcartify.repositoryTesst;

import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.model.Product;
import com.shopcartify.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void testFindByProductNameAndSupermarketCode(){
        String productName = "Milo";
        BigDecimal price = new BigDecimal("100.99");
        String supermarketCode = "eS3j3";
        Product product = createProduct(productName, price, supermarketCode);

        productRepository.save(product);

       Product foundProduct =
                        productRepository.findProductBySupermarketCodeAndProductName(supermarketCode,productName)
                                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

       assertEquals(productName, foundProduct.getProductName());
       assertEquals(supermarketCode, foundProduct.getSupermarketCode());
       assertEquals(price, foundProduct.getProductPrice());


    }

    private Product createProduct(String productName, BigDecimal price, String supermarketCode) {
        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(price);
        product.setProductDescription("new product description");
        product.setProductImageUrl("url from cloudinary");
        product.setSupermarketCode(supermarketCode);

        return product;
    }
}
