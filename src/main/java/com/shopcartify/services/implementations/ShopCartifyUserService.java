package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.ChangePasswordRequest;
import com.shopcartify.dto.reqests.LoginRequest;
import com.shopcartify.dto.reqests.UserProfileUpdateRequest;
import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.dto.responses.AuthenticationResponse;
import com.shopcartify.enums.ExceptionMessage;
import com.shopcartify.enums.UserRole;
import com.shopcartify.exceptions.*;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.messages.MessageService;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.registration.token.VerificationToken;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.repositories.VerificationTokenRepository;
import com.shopcartify.security.config.jwt.JwtService;
import com.shopcartify.services.interfaces.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.AuthenticationException;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.shopcartify.enums.ExceptionMessage.USER_NOT_FOUND_EXCEPTION;
import static com.shopcartify.enums.ExceptionMessage.USER_WITH_EMAIL_NOT_FOUND_EXCEPTION;
import static com.shopcartify.enums.UserRole.ADMIN;
import static com.shopcartify.enums.UserRole.CUSTOMER;
import static com.shopcartify.utils.Constants.APP_NAME;


@Service
@AllArgsConstructor
@Slf4j
public class ShopCartifyUserService implements UserService {

    private static final ExceptionMessage USER_WITH_USERNAME_NOT_FOUND_EXCEPTION = USER_NOT_FOUND_EXCEPTION;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository tokenRepository;

    private final MessageService messageService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    @Override
    public AuthenticationResponse registerUser(UserRegistrationRequest registrationRequest) {
        Optional<ShopCartifyUser> user = userRepository.findByEmail(registrationRequest.getEmail());

        if (user.isPresent()) {
            log.info("user already exists");
            throw new UserAlreadyExistsException(ExceptionMessage.USER_ALREADY_EXISTS);
        }
        Set<UserRole> roles = new HashSet<>();
        roles.add(CUSTOMER);
        String email = registrationRequest.getEmail().toLowerCase().strip();
        ShopCartifyUser newUser = ShopCartifyUser.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .userName(registrationRequest.getUserName())
                .email(email)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles)
                .build();

        var savedUser = userRepository.save(newUser);


        String verificationToken = generateVerificationToken();
        saveUserVerificationToken(savedUser, verificationToken);

        String verificationLink = generateVerificationLink(verificationToken);
        String emailSubject = "Email Verification";
        String senderName = APP_NAME;
        String emailContent = getEmailContent(savedUser.getFirstName(), verificationLink);
        if (savedUser.getEmail() != null && !savedUser.getEmail().isEmpty()) {

            String jwtToken = jwtService.generateToken(savedUser);

            emailService.sendEmail(savedUser.getEmail(), emailSubject, emailContent);
            try {
                emailService.sendEmailForRegistration(savedUser.getEmail(), emailSubject, emailContent);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new ShopCartifyBaseException(e.getMessage());
            }
            return AuthenticationResponse.builder()
                    .Id(savedUser.getUserId())
                    .token(jwtToken)
                    .message("Registration successful. Please check your email for verification.")
                    .build();
        } else {
            log.error("User's email address is missing or empty. Cannot send verification email.");
            return AuthenticationResponse.builder()
                    .message("Registration Not successful. Please Try Again")
                    .Id(savedUser.getUserId())
                    .email(savedUser.getEmail())
                    .build();
        }
    }
    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
    private String generateVerificationLink(String verificationToken) {
        return "http://localhost:5173/dashboard/profile";
    }
    private String getEmailContent(String firstName, String url) {
        return "<p> Hi, " + firstName + ", </p>" +
                "<p> Thank you for registering with us. " +
                "Please click on the link below to complete your registration: </p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> Users Registration Portal Service </p>";
    }
    @Override
    @Transactional
    public ShopCartifyUser markUserAsVerified(String token) throws InvalidTokenException {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new InvalidTokenException("Invalid verification token");
        }

        ShopCartifyUser user = verificationToken.getUser();

        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public AuthenticationResponse userLogin(LoginRequest request) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password", e);
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + request.getEmail() + " not found"));

        var jwtToken = jwtService.generateToken(user);

        UserRole role = null;
        if (user.getRoles().contains(CUSTOMER)) role = CUSTOMER;
        return AuthenticationResponse.builder()
                .Id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(role)
                .token(jwtToken)
                .message("Login Successful")
                .build();
    }
    @Override
    public AuthenticationResponse adminLogin(LoginRequest request) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password", e);
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + request.getEmail() + " not found"));

        var jwtToken = jwtService.generateToken(user);

        UserRole role = null;
        if (user.getRoles().contains(ADMIN)) role = ADMIN;
        return AuthenticationResponse.builder()
                .Id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(role)
                .token(jwtToken)
                .message("Login Successful")
                .build();
    }
    @Override
    public List<ShopCartifyUser> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public ShopCartifyUser findByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException(USER_WITH_USERNAME_NOT_FOUND_EXCEPTION));
    }

    @Override
    public void saveUserVerificationToken(ShopCartifyUser user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }
   @Override
    public String validateToken(String verificationToken) {
        VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (verificationToken == null) return messageService.getInvalidVerificationTokenMessage();

        ShopCartifyUser user = token.getUser();
        Calendar  calendar = Calendar.getInstance();
        if ((token.getTokenExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return messageService.getTokenExpiredMessage();
        }
        var savedUser = userRepository.save(user);
        return "valid";
    }

    @Override
    public ShopCartifyUser updateUserProfile(UserProfileUpdateRequest userProfileRequest, String userEmail) {
        ShopCartifyUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));

        if (userProfileRequest.getFirstName() != null) {
            user.setFirstName(userProfileRequest.getFirstName());
        }
        if (userProfileRequest.getLastName() != null) {
            user.setLastName(userProfileRequest.getLastName());
        }
        if (userProfileRequest.getUserName() != null) {
            user.setUserName(userProfileRequest.getUserName());
        }

        ShopCartifyUser updatedUser = userRepository.save(user);

        return updatedUser;
    }
    @Override
    public ShopCartifyUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION));
    }
    @Override
    public Optional<ShopCartifyUser> findOptionalUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ShopCartifyUser changePassword(Long userId, ChangePasswordRequest changePasswordRequest) throws InvalidPasswordException {
        try {
            ShopCartifyUser user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_WITH_ID_NOT_FOUND_EXCEPTION));

            if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
                throw new InvalidPasswordException("Invalid current password");
            }
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

            return userRepository.save(user);
        } catch (UserNotFoundException exception) {
            throw exception;
        }
    }

    @Override
    public ShopCartifyUser findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_WITH_ID_NOT_FOUND_EXCEPTION));
    }
    @Override
       public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ShopCartifyUser updateSupermarketAdmin(ShopCartifyUser foundUser) {
        return userRepository.save(foundUser);
    }

}
