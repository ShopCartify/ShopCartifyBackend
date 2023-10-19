package com.shopcartify.services.interfaces;


import com.shopcartify.dto.reqests.FindAllRequest;
import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.UpdateProductDetailResponse;
import com.shopcartify.model.Product;
import com.shopcartify.replica.ProductReplica;
import org.springframework.data.domain.Page;

public interface ProductService {
    NewProductResponse addNewProduct(NewProductRequest productRequest);

    UpdateProductDetailResponse updateProductDetail(UpdateProductDetailRequest updateProductDetailRequest);

    Product findProductBySupermarketCodeAndProductName(String supermarketCode, String productName);

    ProductReplica findProductReplicaBySupermarketCodeAndProductName(String supermarketCode, String productName);

    Product findProductByToken(String token);

    Page<Product> findAllProductsWithPaginationAndSortingWithDirection(FindAllRequest findAllProductRequest);

   Product findByProductId(Long productId);
}
