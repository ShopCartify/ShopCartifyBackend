package com.shopcartify.services.implementations;

import com.shopcartify.cloud.CloudService;
import com.shopcartify.dto.reqests.FindAllRequest;
import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.UpdateProductDetailResponse;
import com.shopcartify.exceptions.ProductAlreadyExistsException;
import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.exceptions.ShopCartifyBaseException;
import com.shopcartify.factory.QrCodeGeneratorFactory;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.model.Product;
import com.shopcartify.replica.ProductReplica;
import com.shopcartify.repositories.ProductRepository;
import com.shopcartify.services.interfaces.ProductService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static com.shopcartify.enums.ExceptionMessage.*;
import static com.shopcartify.utils.Constants.IMAGE_PATH;
import static com.shopcartify.utils.QrCodeUtils.*;


@AllArgsConstructor
@Service
@Slf4j
public class ShopCartifyProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CloudService cloudService;
    private final EmailService emailService;

    private final ModelMapper mapper = new ModelMapper();
    @Override
    public NewProductResponse addNewProduct(NewProductRequest newProductRequest) {
        Product savedProduct = null;
        String qrcodeCloudImageUrl = null;
        String productCloudImageUrl = null;
        NewProductResponse newProductResponse = new NewProductResponse();

        boolean isExisting = existBySupermarketCodeAndProductName( newProductRequest.getSupermarketCode(),newProductRequest.getProductName());
           if (isExisting)
                throw new ProductAlreadyExistsException(newProductRequest.getProductName() + PRODUCT_ALREADY_EXIST.getMessage());

           Product product = mapper.map(newProductRequest, Product.class);

           String text = createQrCodeText(product);
           String qrcodeImagePath = null;
           try {
               qrcodeImagePath = IMAGE_PATH + product.getProductName() + product.getSupermarketCode();
               QrCodeGeneratorFactory.generateImageQRCode(text, 300, 300, qrcodeImagePath);

               qrcodeCloudImageUrl = cloudService.upload(new File(qrcodeImagePath));
               productCloudImageUrl = newProductRequest.getProductImageUrl();

               product.setProductQrCodeUrl(qrcodeCloudImageUrl);
               product.setProductImageUrl(productCloudImageUrl);

               savedProduct = productRepository.save(product);
               BeanUtils.copyProperties(savedProduct, newProductResponse);
               newProductResponse.setMessage(PRODUCT_ADDED_SUCCESSFULLY.getMessage());

               sendEmailToSupermarketAdmin( qrcodeCloudImageUrl, newProductRequest.getSupermarketAdminEmail(),  savedProduct);
           }catch (Exception e) {
               log.error(e.getMessage());

           }

            return newProductResponse;

    }

    private boolean existBySupermarketCodeAndProductName(String supermarketCode, String productName) {
       return productRepository.existsBySupermarketCodeAndProductName(supermarketCode, productName);
    }

    @Override
    public UpdateProductDetailResponse updateProductDetail(UpdateProductDetailRequest updateProductDetailRequest) {
        System.out.println( updateProductDetailRequest.getSupermarketCode()+ updateProductDetailRequest.getOldProductName());
        Product foundProduct = productRepository.findProductBySupermarketCodeAndProductName(
                updateProductDetailRequest.getSupermarketCode(), updateProductDetailRequest.getOldProductName())
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        foundProduct.setProductPrice(updateProductDetailRequest.getProductPrice());
        Product savedProduct = productRepository.save(foundProduct);
    var response =  mapper.map(savedProduct, UpdateProductDetailResponse.class);
        response.setMessage("Product Update successful");
       return response;
    }
    @Override
    public Product findProductBySupermarketCodeAndProductName(String supermarketCode, String productName) {
        return productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName)
                .orElseThrow(() ->
                        new ProductNotFoundException( productName + " "+ PRODUCT_NOT_FOUND.getMessage()));

    }
    @Override
    public ProductReplica findProductReplicaBySupermarketCodeAndProductName(String supermarketCode, String productName) {
        Product foundProduct = findProductBySupermarketCodeAndProductName(supermarketCode , productName);
        return mapper.map(foundProduct, ProductReplica.class);
    }

    @Override
    public Product findProductByToken(String token) {

//        String decryptedToken = decrypt(token);
            String productName = extractProductNameFrom(token);
            String supermarketCode = extractSupermarketCodeFrom(token);

            log.info("ProductName: " + productName + " supermarketCode: "+ supermarketCode);

            Product product= productRepository.findProductBySupermarketCodeAndProductName(supermarketCode, productName)
                    .orElseThrow(()-> new ProductNotFoundException(PRODUCT_NOT_FOUND.getMessage()));

            return product;
    }
    @Override
    public Page<Product> findAllProductsWithPaginationAndSortingWithDirection(FindAllRequest findAllProductRequest) {
        return productRepository
                .findAllBySupermarketCode(findAllProductRequest.getSupermarketCode(),PageRequest.of(findAllProductRequest.getOffset(), findAllProductRequest.getPageSite())
                        .withSort(Sort.by(Sort.Direction
                                .fromString(findAllProductRequest.getDirection()),findAllProductRequest.getField())));

    }
//    @Override
//    public Page<Product> findAllProductsWithPaginationAndSortingWithDirection(FindAllRequest findAllProductRequest) {
//        return productRepository
//                .findAll(PageRequest.of(findAllProductRequest.getOffset(), findAllProductRequest.getPageSite())
//                        .withSort(Sort.by(Sort.Direction
//                                .fromString(findAllProductRequest.getDirection()),findAllProductRequest.getField())));
//
//    }
    @Override
    public Product findByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND.getMessage()));
    }


    private void sendEmailToSupermarketAdmin(String qrcode, String adminEmail, Product savedProduct) {
        String subject = "New Product Added: " + savedProduct.getProductName();
        StringBuilder message = new StringBuilder();
        message.append("A new product has been added:\n\n");
        message.append("Product Name: ").append(savedProduct.getProductName()).append("\n");
        message.append("Product Description: ").append(savedProduct.getProductDescription()).append("\n");
        message.append("Product Image URL: ").append(savedProduct.getProductImageUrl()).append("\n");
        message.append("Product Price: ").append(savedProduct.getProductPrice()).append("\n");
        message.append("Product QR Code URL: ").append(savedProduct.getProductQrCodeUrl()).append("\n");

        emailService.sendEmail(adminEmail, subject, message.toString());

        try {
            emailService.sendEmailForNewProduct(qrcode,savedProduct.getProductName(),savedProduct.getSupermarketName(),adminEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ShopCartifyBaseException(e.getMessage());
        }
    }

}
