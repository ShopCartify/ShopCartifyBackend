package com.shopcartify.services.interfaces;


import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.UpdateProductDetailResponse;
import com.shopcartify.model.Product;
import com.shopcartify.replica.ProductReplica;

import java.util.Optional;

public interface ProductService {
    NewProductResponse addNewProduct(NewProductRequest productRequest);

    UpdateProductDetailResponse updateProductDetail(UpdateProductDetailRequest updateProductDetailRequest);

    Product findProductBySupermarketCodeAndProductName(String supermarketCode, String productName);

    ProductReplica findProductReplicaBySupermarketCodeAndProductName(String supermarketCode, String productName);

    Product findProductByToken(String token);

    Optional<Product> findByProductId(Long productId);
}
