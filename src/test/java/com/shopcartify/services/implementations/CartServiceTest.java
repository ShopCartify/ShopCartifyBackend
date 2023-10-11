package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.UpdateCartRequest;
import com.shopcartify.dto.reqests.CartResponse;
import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.services.interfaces.CartService;
import com.shopcartify.services.interfaces.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    private List<String> productNames;
    private  String supermarketCode = "e4d4e";
    private String cartUniqueId = "cartUniqueIdTest";

    @BeforeEach
    public void setUp() {
        productNames = createProducts();
        // four (4) products added;
    }
    @Test
    public void testThatProductCanBeAddedToCart(){

        int initialCartSize = cartService.getCartSize(cartUniqueId);

      CartResponse cartResponse = null;

        cartResponse = cartService.addToCart(createUpdateCartRequest(0));
        assertEquals(initialCartSize + 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(1));
        assertEquals(initialCartSize + 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(2));
        assertEquals(initialCartSize + 1 , cartResponse.getCartSize());

        cartResponse = cartService.addToCart(createUpdateCartRequest(3));
        assertEquals(initialCartSize + 1 , cartResponse.getCartSize());


    }
@Test
public void testRemoveFromCart(){
    int initialCartSize = cartService.getCartSize(cartUniqueId);

    CartResponse cartResponse = null;

    cartResponse = cartService.addToCart(createUpdateCartRequest(0));
    assertEquals(initialCartSize + 1 , cartResponse.getCartSize());

    cartResponse = cartService.addToCart(createUpdateCartRequest(1));
    assertEquals(initialCartSize + 1 , cartResponse.getCartSize());

    cartResponse = cartService.removeFromCart(createUpdateCartRequest(0));
    assertEquals(initialCartSize -1 , cartResponse.getCartSize());

    cartResponse = cartService.removeFromCart(createUpdateCartRequest(1));
    assertEquals(initialCartSize -1 , cartResponse.getCartSize());
}
    private UpdateCartRequest createUpdateCartRequest(int position) {
        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setCartUniqueId(cartUniqueId);
        updateCartRequest.setProductName(productNames.get(position));
        updateCartRequest.setSupermarketCode(supermarketCode);

        return updateCartRequest;
    }

    private List<String> createProducts(){
    List<String> productNames = new ArrayList<String>();

    String productName = "Laptop";
    BigDecimal price = new BigDecimal("3000.99");
    NewProductRequest productRequest = createProductRequest(productName, price);

    NewProductResponse newProductResponse =  productService.addNewProduct(productRequest);
    productNames.add(productName);

    productName = "slippers";
    price = new BigDecimal("45600.99");
    productRequest = createProductRequest(productName, price);

    newProductResponse =  productService.addNewProduct(productRequest);
    productNames.add(productName);

    productName = "Iphone";
    price = new BigDecimal("13000.99");
    productRequest = createProductRequest(productName, price);

    newProductResponse =  productService.addNewProduct(productRequest);
    productNames.add(productName);

    productName = "shoe";
    price = new BigDecimal("53000.99");
    productRequest = createProductRequest(productName, price);

    newProductResponse =  productService.addNewProduct(productRequest);
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
