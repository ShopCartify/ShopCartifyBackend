package com.shopcartify.registration;


import com.shopcartify.dto.reqests.LoginRequest;
import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.dto.responses.AuthenticationResponse;
import com.shopcartify.dto.responses.UserRegistrationResponse;
import com.shopcartify.exceptions.InvalidTokenException;
import com.shopcartify.exceptions.UserAlreadyExistsException;
import com.shopcartify.exceptions.UserNotFoundException;
import com.shopcartify.messages.MessageService;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.registration.token.VerificationToken;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.repositories.VerificationTokenRepository;
import com.shopcartify.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RegistrationController {

private final UserService userService;


private final VerificationTokenRepository tokenRepository;

private final MessageService messageService;

private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {

        try {
            UserRegistrationResponse response = new UserRegistrationResponse();
            response.setMessage("Registration Successful");

            return new ResponseEntity<>(userService.registerUser(registrationRequest), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthenticationResponse("User with this email already exists."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponse("An error occurred during registration."));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthenticationResponse response = userService.userLogin(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/admin_login")
    public ResponseEntity<AuthenticationResponse> adminLogin(@RequestBody LoginRequest loginRequest) {
        try {
            AuthenticationResponse response = userService.adminLogin(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) throws InvalidTokenException {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token. Please try again.");
        }
        if (verificationToken.getUser().isEnabled()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Account is already verified.");
        }

        String verificationResult = userService.validateToken(token);

        if ("valid".equalsIgnoreCase(verificationResult)) {
            ShopCartifyUser user = userService.markUserAsVerified(token);

            String frontendDashboardUrl = "http://localhost:5173/dashboard/profile";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", frontendDashboardUrl)
                    .body("Email verified. Redirecting to the dashboard...");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token or verification error. Please try again.");
        }
    }

}
