package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.exceptions.CartNotFoundException;
import com.shopcartify.exceptions.ProductAlreadyExistsException;
import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.model.Cart;
import com.shopcartify.model.Product;
import com.shopcartify.repositories.ProductRepository;
import com.shopcartify.services.interfaces.CartService;
import com.shopcartify.services.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class CartServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductRepository productRepository;

    private List<String> productNames;
    private  String supermarketCode = "e4d4e";
    private Random random = new Random();

    @BeforeEach
    public void setUp() {
        productNames = createProducts();
        // four (4) products added;
    }
    @Test
    public void testThatVirtualCartCanBeCreated() {
        CartResponse cartResponse = cartService.addToCart(createUpdateCartRequest(0, null));
        assertTrue( cartResponse.getCartSize()>0);
        assertNotNull(cartResponse.getUniqueCartId());

        Cart cart = cartService.findCartByUniqueCartId(cartResponse.getUniqueCartId());

        assertNotNull(cart);
        assertNotNull(cart.getCartProductIds().get(0));

    }
    @Test
    public void testThatProductCanBeAddedToCart(){
        int cartSize = 0 ;


      CartResponse cartResponse = null;

        cartResponse = cartService.addToCart(createUpdateCartRequest(0, null));
        assertEquals(cartSize += 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(1, cartResponse.getUniqueCartId()));
        assertEquals(cartSize += 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(2, cartResponse.getUniqueCartId()));
        assertEquals(cartSize += 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(3, cartResponse.getUniqueCartId()));
        assertEquals(cartSize += 1 , cartResponse.getCartSize());

        try{
            cartSize = cartService.getCartSize(cartResponse.getUniqueCartId());
            assertEquals(4, cartSize);
        }catch (CartNotFoundException exception){
            log.error(exception.getMessage());
        }


    }
@Test
public void testRemoveFromCart(){

    int cartSize = 0 ;

    CartResponse cartResponse = null;

    cartResponse = cartService.addToCart(createUpdateCartRequest(0, null));
    assertEquals(cartSize +=1 , cartResponse.getCartSize());

    try{
        cartSize = cartService.getCartSize(cartResponse.getUniqueCartId());

    }catch (CartNotFoundException exception){
        log.error(exception.getMessage());
    }

    cartResponse = cartService.addToCart(createUpdateCartRequest(1, cartResponse.getUniqueCartId()));
    assertEquals(cartSize += 1 , cartResponse.getCartSize());
    cartResponse = cartService.removeFromCart(createUpdateCartRequest(0, cartResponse.getUniqueCartId()));
    Cart cart = cartService.findCartByUniqueCartId(cartResponse.getUniqueCartId());

    assertEquals(cartSize -=1 , cartResponse.getCartSize());

    cartResponse = cartService.removeFromCart(createUpdateCartRequest(1,cartResponse.getUniqueCartId()));
    assertEquals(cartSize -=1 , cartResponse.getCartSize());
}
    private UpdateCartRequest createUpdateCartRequest(int position, String cartUniqueId ) {
        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setUniqueCartId(cartUniqueId);
        updateCartRequest.setProductName(productNames.get(position));
        updateCartRequest.setNumberOfProducts(1 + random.nextInt(10));
        updateCartRequest.setSupermarketCode(supermarketCode);

        return updateCartRequest;
    }

    private List<String> createProducts(){
    List<String> productNames = new ArrayList<String>();

    String productName = "book";
    BigDecimal price = new BigDecimal("300.99");
    NewProductRequest productRequest = createProductRequest(productName, price);

        NewProductResponse newProductResponse = null;

        Optional<Product> optionalProduct = productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName);
        if (optionalProduct.isEmpty()) {
            productService.addNewProduct(productRequest);
        }
        productNames.add(productName);

    productName = "slippers";
    price = new BigDecimal("45600.99");
    productRequest = createProductRequest(productName, price);

       optionalProduct = productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName);
        if (optionalProduct.isEmpty()) {
            productService.addNewProduct(productRequest);
        }
    productNames.add(productName);


        productName = "Iphone";
    price = new BigDecimal("13000.99");
    productRequest = createProductRequest(productName, price);


     optionalProduct = productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName);
        if (optionalProduct.isEmpty()) {
            productService.addNewProduct(productRequest);
        }
        productNames.add(productName);

        productName = "shoe";
    price = new BigDecimal("53000.99");
    productRequest = createProductRequest(productName, price);


     optionalProduct = productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName);
        if (optionalProduct.isEmpty()) {
            productService.addNewProduct(productRequest);
        }
        productNames.add(productName);
        return productNames;
}


    private NewProductRequest createProductRequest(String productName, BigDecimal price) {
        NewProductRequest productRequest = new NewProductRequest();
        productRequest.setProductName(productName);
        productRequest.setProductPrice(price);
        productRequest.setProductDescription("new product description");
        productRequest.setProductImageUrl("url from cloudinary");
        productRequest.setSupermarketCode(supermarketCode);

        return productRequest;
    }

}
