package com.shopcartify.services.implementations;


import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.UpdateProductDetailResponse;
import com.shopcartify.model.Product;
import com.shopcartify.replica.ProductReplica;
import com.shopcartify.services.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setUp(){

    }
    @Test
    public void testProductCanBeCreated(){
        String productName = "Milo";
        BigDecimal price = new BigDecimal("100.99");
        NewProductRequest productRequest = createProductRequest(productName, price);

        NewProductResponse newProductResponse =  productService.addNewProduct(productRequest);
        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(price, newProductResponse.getProductPrice());
    }
    @Test
    public void testFindProductByProductName(){
        String productName = "Ice Cream";
        BigDecimal price = new BigDecimal("105.39");
        NewProductRequest productRequest = createProductRequest(productName, price);

        NewProductResponse newProductResponse =  productService.addNewProduct(productRequest);
        ProductReplica productReplica = productService.findProductReplicaBySupermarketCodeAndProductName(
                productRequest.getSupermarketCode(),productName);

        assertEquals(productName, productReplica.getProductName());
        assertEquals(price, productReplica.getProductPrice());
    }

    @Test
    public void testProductPriceCanBeChanged(){
        String productName = "Bottle Water";
        BigDecimal price = new BigDecimal("100.99");
        NewProductRequest productRequest = createProductRequest(productName, price);

        NewProductResponse newProductResponse =  productService.addNewProduct(productRequest);
        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(price, newProductResponse.getProductPrice());

        UpdateProductDetailRequest updateProductDetailRequest = new UpdateProductDetailRequest();
        updateProductDetailRequest.setProductPrice(new BigDecimal("150.88"));
        updateProductDetailRequest.setOldProductName(productName);
        updateProductDetailRequest.setSupermarketCode(productRequest.getSupermarketCode());
        UpdateProductDetailResponse updateProductResponse = productService.updateProductDetail(updateProductDetailRequest);

        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(new BigDecimal("150.88"), updateProductResponse.getProductPrice());

        Product product = productService.findProductBySupermarketCodeAndProductName(productRequest.getSupermarketCode(),productName);

        assertEquals(new BigDecimal("150.88"), product.getProductPrice());

    }
    @Test
    public void testProductCanBeUpdated(){
        String productName = "trash";
        BigDecimal price = new BigDecimal("2100.99");
        NewProductRequest productRequest = createProductRequest(productName, price);

        NewProductResponse newProductResponse =  productService.addNewProduct(productRequest);
        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(price, newProductResponse.getProductPrice());

        UpdateProductDetailRequest updateProductDetailRequest = new UpdateProductDetailRequest();
        updateProductDetailRequest.setProductPrice(new BigDecimal("3150.88"));
        updateProductDetailRequest.setOldProductName(productName);
        updateProductDetailRequest.setNewProductName( "refuse bin");
        updateProductDetailRequest.setSupermarketCode(productRequest.getSupermarketCode());

        UpdateProductDetailResponse updateProductResponse = productService.updateProductDetail(updateProductDetailRequest);

        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(new BigDecimal("3150.88"), updateProductResponse.getProductPrice());

        Product product = productService.findProductBySupermarketCodeAndProductName(productRequest.getSupermarketCode(),productName);

        assertEquals(new BigDecimal("3150.88"), product.getProductPrice());
//        assertEquals(updateProductDetailRequest.getNewProductName(), newProductResponse.getProductName());
        assertEquals(productRequest.getSupermarketCode(),updateProductResponse.getSupermarketCode());

    }
    private NewProductRequest createProductRequest(String productName, BigDecimal price) {
        NewProductRequest productRequest = new NewProductRequest();
        productRequest.setProductName(productName);
        productRequest.setProductPrice(price);
        productRequest.setProductDescription("new product description");
        productRequest.setProductImageUrl("url from cloudinary");
        productRequest.setSupermarketCode("e4d4e");

        return productRequest;
    }
}
