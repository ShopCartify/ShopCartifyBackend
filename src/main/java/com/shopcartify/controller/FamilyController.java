//package com.shopcartify.controller;
//
//import com.shopcartify.dto.reqests.FamilyRegistrationRequest;
//import com.shopcartify.dto.responses.FamilyRegistrationResponse;
//import com.shopcartify.exceptions.FamilyAlreadyExistException;
//import com.shopcartify.exceptions.FamilyNotFoundException;
//import com.shopcartify.exceptions.UserNotFoundException;
//import com.shopcartify.services.interfaces.FamilyServices;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/family")
//@RequiredArgsConstructor
//public class FamilyController {
//
//    private final FamilyServices familyServices;
//
//    @PostMapping("/create-family")
//    public ResponseEntity<FamilyRegistrationResponse> createFamily(@RequestBody FamilyRegistrationRequest registrationRequest) {
//        try {
//            FamilyRegistrationResponse response = familyServices.createFamily(registrationRequest);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (FamilyAlreadyExistException ex) {
//            return new ResponseEntity<>(FamilyRegistrationResponse.builder()
//                    .message(ex.getMessage())
//                    .build(), HttpStatus.CONFLICT);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(FamilyRegistrationResponse.builder()
//                    .message("An error occurred while creating the family account.")
//                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//}
//
//    @PostMapping("/add-member")
//    public ResponseEntity<String> addMemberToFamily(@RequestParam String familyName, @RequestParam String username) {
//        try {
//            familyServices.addMemberToFamily(familyName, username);
//            return ResponseEntity.ok("Member added to the family successfully");
//        } catch (FamilyNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found");
//        } catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the member");
//        }
//    }
//}
