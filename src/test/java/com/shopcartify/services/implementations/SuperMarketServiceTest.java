package com.shopcartify.services.implementations;


import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.model.Product;
import com.shopcartify.model.Supermarket;
import com.shopcartify.repositories.SupermarketRepository;
import com.shopcartify.services.interfaces.SupermarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ActiveProfiles("dev")
@SpringBootTest
public class SuperMarketServiceTest {
    @Autowired
    private SupermarketService supermarketService;
    @Autowired
    private SupermarketRepository supermarketRepository;
    private Supermarket supermarket;
    
    @BeforeEach
    public void setUp(){
        supermarket = createSupermarket();
    }
      @Test
    public void testSuperMarketCanRegister() {
        String superMarketName = "Fiddovea Supermarket";
        SupermarketRegistrationRequest supermarketRegistrationRequest = createSupermarketRegistrationRequest(superMarketName);
        SupermarketRegistrationResponse supermarketRegistrationResponse = supermarketService.registerNewSupermarket(supermarketRegistrationRequest);

        assertEquals(supermarketRegistrationResponse.getSupermarketEmail(), supermarketRegistrationRequest.getSupermarketEmail());
        assertEquals(supermarketRegistrationResponse.getSupermarketName(), supermarketRegistrationRequest.getSupermarketName());
    }
    @Test
    public void testSupermarketCanAddNewProduct() {
        String productName = "Milo";
        BigDecimal price = new BigDecimal("100.99");
        NewProductRequest productRequest = createProductRequest(productName, price,supermarket.getSupermarketCode() );

        NewProductResponse newProductResponse =  supermarketService.addNewProduct(productRequest);
        Product foundProduct = supermarketService.findProductByToken(supermarket.getSupermarketCode()+productName+".");

        assertEquals(productName, newProductResponse.getProductName());
        assertEquals(price, newProductResponse.getProductPrice());

        assertEquals(supermarket.getSupermarketCode(), foundProduct.getSupermarketCode());
        assertEquals(productName, foundProduct.getProductName());
        assertEquals(price, foundProduct.getProductPrice());
    }
    @Test
    public void testFindSupermarketBySupermarketCode(){
        String superMarketName = "Shopify Supermarket";
        SupermarketRegistrationRequest supermarketRegistrationRequest = createSupermarketRegistrationRequest(superMarketName);
        SupermarketRegistrationResponse supermarketRegistrationResponse = supermarketService.registerNewSupermarket(supermarketRegistrationRequest);

        Supermarket foundSupermarket = supermarketService
                                        .findSupermarketBySupermarketCode(supermarketRegistrationResponse.getSupermarketCode());

        assertNotNull(foundSupermarket);
        assertEquals(foundSupermarket.getSupermarketName(), superMarketName);
    }


    private SupermarketRegistrationRequest createSupermarketRegistrationRequest(String superMarketName) {
        return  SupermarketRegistrationRequest.builder()
                .supermarketName(superMarketName)
                .supermarketEmail("supermarketemail@supermarket.com")
                .cacUrl("cacurl://supermarket")
                .build();
    }
    private NewProductRequest createProductRequest(String productName, BigDecimal price, String supermarketCode) {
        NewProductRequest productRequest = new NewProductRequest();
        productRequest.setProductName(productName);
        productRequest.setProductPrice(price);
        productRequest.setProductDescription("new product description");
        productRequest.setProductImageUrl("url from cloudinary");
        productRequest.setSupermarketCode(supermarketCode);

        return productRequest;
    }
    private Supermarket createSupermarket(){
        Supermarket supermarket = Supermarket.builder()
                .supermarketCode("dD2e4")
                .supermarketName("Supermarket")
                .supermarketEmail("email from Supermarket")
                .build();

        Optional<Supermarket> foundSupermarket = supermarketRepository.findSupermarketBySupermarketName(supermarket.getSupermarketName());
        return foundSupermarket.orElseGet(() -> supermarketRepository.save(supermarket));
    }
}
