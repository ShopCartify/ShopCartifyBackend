package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.LoginRequest;
import com.shopcartify.dto.reqests.UserProfileUpdateRequest;
import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.dto.responses.AuthenticationResponse;
import com.shopcartify.exceptions.InvalidTokenException;
import com.shopcartify.exceptions.UserAlreadyExistsException;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.messages.MessageService;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.registration.token.VerificationToken;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.repositories.VerificationTokenRepository;
import com.shopcartify.security.config.jwt.JwtService;
import com.shopcartify.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Transactional
class ShopCartifyUserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private  VerificationTokenRepository tokenRepository;


    private  TokenService tokenService;

    @Autowired
    private MessageService messageService;

    @Test
    public void testRegisterUser_Success() {
        String email = "osisiogubenjamin1@gmail.com";
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setFirstName("John");
        registrationRequest.setLastName("Doe");
        registrationRequest.setUserName("johndoe");
        registrationRequest.setEmail(email);
        registrationRequest.setPassword(passwordEncoder.encode("password"));

        AuthenticationResponse response = userService.registerUser(registrationRequest);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("Registration successful. Please check your email for verification.", response.getMessage());

        Optional<ShopCartifyUser> savedUserOptional = userRepository.findByEmail(email);
        assertTrue(savedUserOptional.isPresent());

        ShopCartifyUser savedUser = savedUserOptional.get();
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("johndoe", savedUser.getUserName());
        assertEquals(email, savedUser.getEmail());

        userRepository.delete(savedUser);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        ShopCartifyUser existingUser = new ShopCartifyUser();
        existingUser.setEmail("existing@example.com");
        userRepository.save(existingUser);

        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setEmail("existing@example.com");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationRequest));

        userRepository.delete(existingUser);
    }

    @Test
    @Transactional
    public void testMarkUserAsVerified_Success() throws InvalidTokenException {
        ShopCartifyUser user = new ShopCartifyUser();
        user.setEmail("test@example.com");
        userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken("testToken");
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

        ShopCartifyUser verifiedUser = userService.markUserAsVerified("testToken");

        assertNotNull(verifiedUser);
        assertTrue(verifiedUser.isEnabled());

        userRepository.delete(user);
        tokenRepository.delete(verificationToken);
    }

//    @Test
//    public void testLogin_Success() throws AuthenticationException {
//        ShopCartifyUser user = new ShopCartifyUser();
//        user.setEmail("test@example.com");
//        user.setPassword(passwordEncoder.encode("password"));
//        userRepository.save(user);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword(passwordEncoder.encode("password"));
//
//        AuthenticationResponse response = userService.login(loginRequest);
//
//        assertNotNull(response);
//        assertNotNull(response.getToken());
//        assertEquals("Login Successful", response.getMessage());
//
//        userRepository.delete(user);
//    }

    @Test
    public void testGetAllUsers() {
        List<ShopCartifyUser> freshUsers = userService.getAllUsers();
        ShopCartifyUser user1 = new ShopCartifyUser();
        user1.setEmail("user1@example.com");
        userRepository.save(user1);

        ShopCartifyUser user2 = new ShopCartifyUser();
        user2.setEmail("user2@example.com");
        userRepository.save(user2);

        List<ShopCartifyUser> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(freshUsers.size()+2, users.size());

        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @Test
    public void testFindByUserName_UserFound() {
        ShopCartifyUser user = new ShopCartifyUser();
        user.setUserName("testUser");
        userRepository.save(user);

        ShopCartifyUser foundUser = userService.findByUserName("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUserName());

        userRepository.delete(user);
    }



    @Test
    public void testUpdateUserProfile() {
        ShopCartifyUser user = new ShopCartifyUser();
        user.setEmail("test@example.com");
        userRepository.save(user);

        UserProfileUpdateRequest updateRequest = new UserProfileUpdateRequest();
        updateRequest.setFirstName("UpdatedFirstName");
        updateRequest.setLastName("UpdatedLastName");
        updateRequest.setUserName("UpdatedUserName");

        ShopCartifyUser updatedUser = userService.updateUserProfile(updateRequest, "test@example.com");

        assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedUser.getLastName());
        assertEquals("UpdatedUserName", updatedUser.getUserName());

        userRepository.delete(user);
    }


    @Test
    public void testFindByEmail_UserFound() {
        ShopCartifyUser user = new ShopCartifyUser();
        user.setEmail("test@example.com");
        userRepository.save(user);

        ShopCartifyUser foundUser = userService.findByEmail("test@example.com");

        assertEquals("test@example.com", foundUser.getEmail());
        userRepository.delete(user);
    }

    @Test
    public void testDeleteUserById_UserDeleted() {
        ShopCartifyUser user = new ShopCartifyUser();
        user.setEmail("test@example.com");
        userRepository.save(user);

        userService.deleteUserById(user.getUserId());

        assertFalse(userRepository.findById(user.getUserId()).isPresent());
    }

}