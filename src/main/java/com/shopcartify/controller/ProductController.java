package com.shopcartify.controller;
import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.ShopCartifyApiResponse;
import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.exceptions.ShopCartifyBaseException;
import com.shopcartify.services.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/productController")

public class ProductController {
    private final ProductService productService;

    @PostMapping("/addNewProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody NewProductRequest productRequest){

        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, productService.addNewProduct(productRequest))
                    , HttpStatus.OK);
        }catch (ShopCartifyBaseException shopCartifyBaseException){
            return new ResponseEntity<>((new ShopCartifyApiResponse(false,shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }

    }



    @GetMapping("/findProductByToken/{token}")
    public ResponseEntity<?> findProductByToken(
            @PathVariable String token){
        try {
            return new ResponseEntity<>(
                    new ShopCartifyApiResponse(true,
                    productService.findProductByToken(token)), HttpStatus.OK);
        }catch (ProductNotFoundException productNotFoundException){
            return new ResponseEntity<>(
                    new ShopCartifyApiResponse( false,
                    productNotFoundException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
