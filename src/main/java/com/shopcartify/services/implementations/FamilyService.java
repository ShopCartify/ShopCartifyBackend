//package com.shopcartify.services.implementations;
//
//import com.shopcartify.dto.reqests.FamilyRegistrationRequest;
//import com.shopcartify.dto.responses.FamilyRegistrationResponse;
//import com.shopcartify.enums.ExceptionMessage;
//import com.shopcartify.exceptions.FamilyNotFoundException;
//import com.shopcartify.exceptions.UserNotFoundException;
//import com.shopcartify.model.Family;
//import com.shopcartify.model.ShopCartifyUser;
//import com.shopcartify.repositories.FamilyRepository;
//import com.shopcartify.repositories.UserRepository;
//import com.shopcartify.services.interfaces.FamilyServices;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class FamilyService implements FamilyServices {
//
//    private final FamilyRepository familyRepository;
//
//    private final UserRepository userRepository;
//    @Override
//    public FamilyRegistrationResponse createFamily(FamilyRegistrationRequest registrationRequest) {
//        String familyName = registrationRequest.getFamilyName();
//        boolean familyExists = familyRepository.existsByFamilyName(familyName);
//        if (familyExists) {
//            return FamilyRegistrationResponse.builder()
//                    .message("Family with the provided name already exists.")
//                    .build();
//        }
//        Family newFamily = new Family();
//        newFamily.setFamilyName(familyName);
//        familyRepository.save(newFamily);
//
//        return FamilyRegistrationResponse.builder()
//                .message("Family account created successfully.")
//                .build();
//    }
//    @Override
//    public void addMemberToFamily(String familyName, String username) {
//        Family family = familyRepository.findByFamilyName(familyName);
//        if (family == null) {
//            throw new FamilyNotFoundException(ExceptionMessage.FAMILY_NOT_FOUND_EXCEPTION);
//        }
//        Optional<ShopCartifyUser> user = userRepository.findByUserName(username);
//        if (user.isEmpty()) {
//            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION);
//        }
//        family.getMembers().add(user.get());
//        familyRepository.save(family);
//    }
//
//}
//
//
