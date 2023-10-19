package com.shopcartify.services.interfaces;

import com.shopcartify.dto.reqests.*;
import com.shopcartify.dto.responses.AuthenticationResponse;
import com.shopcartify.exceptions.InvalidPasswordException;
import com.shopcartify.exceptions.InvalidTokenException;
import com.shopcartify.model.ShopCartifyUser;
import org.springframework.data.domain.Page;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse registerUser (UserRegistrationRequest registrationRequest);

    ShopCartifyUser changePassword(Long userId, ChangePasswordRequest changePasswordRequest) throws InvalidPasswordException;

    AuthenticationResponse adminLogin(LoginRequest request) throws org.springframework.security.core.AuthenticationException;

    List<ShopCartifyUser> getAllUsers();
    Page<ShopCartifyUser> findAllUsers(FindAllRequest findAllUserRequest);

    ShopCartifyUser updateUserProfile(UserProfileUpdateRequest userProfileRequest, String userEmail);

    ShopCartifyUser findByEmail(String email);

    ShopCartifyUser findByUserName(String username);

    void saveUserVerificationToken(ShopCartifyUser user, String verificationToken);

    String validateToken(String verificationToken);

    Optional<ShopCartifyUser> findOptionalUserByEmail(String email);
    AuthenticationResponse userLogin(LoginRequest request) throws AuthenticationException;

    ShopCartifyUser markUserAsVerified(String token) throws InvalidTokenException;

    ShopCartifyUser findUserById(Long userId);

    void deleteUserById(Long id);

    ShopCartifyUser updateSupermarketAdmin(ShopCartifyUser foundUser);
}
