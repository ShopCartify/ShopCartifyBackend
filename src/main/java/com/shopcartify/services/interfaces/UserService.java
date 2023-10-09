package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.ChangePasswordRequest;
import com.shopcartify.dto.reqests.LoginRequest;
import com.shopcartify.dto.reqests.UserProfileUpdateRequest;
import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.dto.responses.AuthenticationResponse;
import com.shopcartify.exceptions.InvalidPasswordException;
import com.shopcartify.exceptions.InvalidTokenException;
import com.shopcartify.model.ShopCartifyUser;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse registerUser (UserRegistrationRequest registrationRequest);

    ShopCartifyUser changePassword(Long userId, ChangePasswordRequest changePasswordRequest) throws InvalidPasswordException;

    List<ShopCartifyUser> getAllUsers();

    ShopCartifyUser updateUserProfile(UserProfileUpdateRequest userProfileRequest, String userEmail);

    ShopCartifyUser findByEmail(String email);

    ShopCartifyUser findByUserName(String username);

    void saveUserVerificationToken(ShopCartifyUser user, String verificationToken);

    String validateToken(String verificationToken);

    Optional<ShopCartifyUser> findOptionalUserByEmail(String email);
    AuthenticationResponse login(LoginRequest request) throws AuthenticationException;

    ShopCartifyUser markUserAsVerified(String token) throws InvalidTokenException;

    ShopCartifyUser findUserById(Long userId);

    void deleteUserById(Long id);

    ShopCartifyUser updateSupermarketAdmin(ShopCartifyUser foundUser);
}
