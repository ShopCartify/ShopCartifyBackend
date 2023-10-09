package com.shopcartify.controller;

import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.reqests.UpdateProductDetailRequest;
import com.shopcartify.dto.responses.ShopCartifyApiResponse;
import com.shopcartify.exceptions.ShopCartifyBaseException;
import com.shopcartify.model.Transaction;
import com.shopcartify.services.interfaces.ProductService;
import com.shopcartify.services.interfaces.TransactionService;
import com.shopcartify.services.interfaces.adminServices.SupermarketAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/supermarketAdminController")
public class SupermarketAdminController {
    private final SupermarketAdminService supermarketAdminService;
    private final ProductService productService;
    private final TransactionService transactionService;

    @PostMapping("/registerSupermarketAdmin")
    public ResponseEntity<?> registerSupermarketAdmin(@RequestBody SupermarketRegistrationRequest superMarketAdminRegistrationRequest) {
        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, supermarketAdminService.registerSupermarketAdmin(superMarketAdminRegistrationRequest))
                    , HttpStatus.OK);
        } catch (ShopCartifyBaseException shopCartifyBaseException) {
            return new ResponseEntity<>((new ShopCartifyApiResponse(false, shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/confirmNewSupermarketAdmin/{token}")
    public ResponseEntity<?> confirmNewSupermarketAdmin(@PathVariable String token) {
        return new ResponseEntity<>(new ShopCartifyApiResponse(true, supermarketAdminService.confirmNewSupermarketAdmin(token))
                , HttpStatus.OK);
    }

    @PostMapping("/addNewProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody NewProductRequest productRequest) {

        try {
            return new ResponseEntity<>(new ShopCartifyApiResponse(true, productService.addNewProduct(productRequest))
                    , HttpStatus.OK);
        } catch (ShopCartifyBaseException shopCartifyBaseException) {
            return new ResponseEntity<>((new ShopCartifyApiResponse(false, shopCartifyBaseException.getMessage()))
                    , HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/updateProductDetail")
    public ResponseEntity<?> updateProductDetail(@RequestBody UpdateProductDetailRequest updateProductDetailRequest){
        try {
            return new ResponseEntity<>(
                    productService.updateProductDetail(updateProductDetailRequest), HttpStatus.OK);
        }catch (ShopCartifyBaseException shopCartifyBaseException){
            return new ResponseEntity<>(shopCartifyBaseException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/transactions")
//    public String viewAllTransactions(Model model) {
//        List<Transaction> transactions = transactionService.getAllTransactions();
//        model.addAttribute("transactions", transactions);
//        return "supermarket-admin/transaction-history";
//    }
}
