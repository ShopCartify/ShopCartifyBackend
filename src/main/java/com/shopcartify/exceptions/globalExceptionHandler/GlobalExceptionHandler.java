//package com.shopcartify.exceptions.globalExceptionHandler;
//
//import com.shopcartify.dto.responses.ShopCartifyApiResponse;
//import com.shopcartify.exceptions.ShopCartifyBaseException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(ShopCartifyBaseException.class)
//    public ResponseEntity<?> ShopCartifyBaseException(ShopCartifyBaseException shopCartifyBaseException) {
//        LOGGER.error(shopCartifyBaseException.getMessage());
//        return new ResponseEntity<>((new ShopCartifyApiResponse(false,shopCartifyBaseException.getMessage()))
//                , HttpStatus.NOT_FOUND);
//    }
//}
