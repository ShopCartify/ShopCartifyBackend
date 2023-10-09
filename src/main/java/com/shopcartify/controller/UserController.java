package com.shopcartify.controller;


import com.shopcartify.dto.reqests.ChangePasswordRequest;
import com.shopcartify.dto.reqests.UserProfileUpdateRequest;
import com.shopcartify.exceptions.InvalidPasswordException;
import com.shopcartify.exceptions.UserNotFoundException;
import com.shopcartify.exceptions.UserProfileUpdateException;
import com.shopcartify.model.*;
import com.shopcartify.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    private final TransactionService transactionService;

    private final ReceiptService receiptService;

    private final WishlistService wishlistService;

    private final ProductService productService;

    @GetMapping("/find-by-id/{userId}")
    public ResponseEntity<ShopCartifyUser> findUserById(@PathVariable Long userId) {
        try {
            ShopCartifyUser user = userService.findUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find-by-user-email")
    public ResponseEntity<?> findUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ShopCartifyUser>>getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<ShopCartifyUser> findUserByUsername(@RequestParam("username") String username) {
        return new ResponseEntity<>(userService.findByUserName(username), HttpStatus.OK);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ShopCartifyUser> updateUserProfileByEmail(
            @RequestParam("email") String userEmail,
            @RequestBody UserProfileUpdateRequest userProfileRequest) {

        try {
            ShopCartifyUser updatedUser = userService.updateUserProfile(userProfileRequest, userEmail);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserProfileUpdateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ShopCartifyUser> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordRequest changePasswordRequest) throws InvalidPasswordException {
        ShopCartifyUser updatedUser = userService.changePassword(userId, changePasswordRequest);
        return ResponseEntity.ok(updatedUser);
    }

//    @GetMapping("/transactions")
//    public String viewTransactionHistory(Model model, Principal principal) {
//        String email = principal.getName();
//        ShopCartifyUser customer = userService.findByEmail(email);
//        List<Transaction> transactions = transactionService.getTransactionHistoryForUser(customer);
//        model.addAttribute("transactions", transactions);
//        return "customer/transaction-history";
//    }

    @PostMapping("/make-payment")
    public String makePayment(Model model, Principal principal, @RequestBody List<ReceiptItems> items, @RequestParam BigDecimal totalAmount) {
        String email = principal.getName();
        ShopCartifyUser customer = userService.findByEmail(email);
        Receipt receipt = receiptService.createReceipt(customer, items, totalAmount);
        model.addAttribute("receipt", receipt);
        return "customer/receipt";
    }

//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable Long id){
//        userService.deleteUserById(id);
//    }

//    @PostMapping("/add")
//    public ResponseEntity<WishList> addToWishlist(
//            @RequestParam Long customerId,
//            @RequestParam Long productId
//    ) {
//        ShopCartifyUser shopCartifyUser = userService.findUserById(customerId);
//        Optional<Product> product = productService.findByProductId(productId);
//
//        if (shopCartifyUser != null && product.isPresent()) {
//            WishList wishlist = wishlistService.addToWishlist(shopCartifyUser, product);
//            if (wishlist != null) {
//                return ResponseEntity.ok(wishlist);
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }

//    @PostMapping("/remove")
//    public ResponseEntity<WishList> removeFromWishlist(
//            @RequestParam Long customerId,
//            @RequestParam Long productId
//    ) {
//        ShopCartifyUser shopCartifyUser = userService.findUserById(customerId);
//        Optional<Product> product = productService.findByProductId(productId);
//
//        if (shopCartifyUser != null && product.isPresent()) {
//            WishList wishlist = wishlistService.removeFromWishlist(shopCartifyUser, product.get());
//            if (wishlist != null) {
//                return ResponseEntity.ok(wishlist);
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }

}
