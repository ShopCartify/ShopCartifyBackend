package com.shopcartify.services.implementations.adminServices;

import com.shopcartify.dto.reqests.AdminLoginRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.responses.AdminConfirmationResponse;
import com.shopcartify.dto.responses.AdminLoginResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.exceptions.ShopCartifyBaseException;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Supermarket;
import com.shopcartify.services.interfaces.SupermarketService;
import com.shopcartify.services.interfaces.UserService;
import com.shopcartify.services.interfaces.adminServices.SupermarketAdminService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.shopcartify.enums.UserRole.SUPERMARKET_ADMIN;
import static com.shopcartify.utils.Constants.*;


@AllArgsConstructor
@Service
@Slf4j
public class ShopCartifySupermarketAdminService implements SupermarketAdminService {
    public final SupermarketService supermarketService;
    public final UserService userService;
//    private final PasswordEncoder passwordEncoder;
private final EmailService emailService;

    @Override
    public SupermarketRegistrationResponse registerSupermarketAdmin(SupermarketRegistrationRequest supermarketAdminRegistrationRequest) {

        SupermarketRegistrationResponse supermarketRegistrationResponse = supermarketService.registerNewSupermarket(supermarketAdminRegistrationRequest);

        ShopCartifyUser foundUser = userService.findUserById(supermarketAdminRegistrationRequest.getRegisteredUserId());
            String supermarketCode = supermarketRegistrationResponse.getSupermarketCode() ;
            String emailUrl = sendUserEmail( supermarketCode, foundUser, supermarketAdminRegistrationRequest.getSupermarketEmail());
            supermarketRegistrationResponse.setEmailOfRegisteredUser(foundUser.getEmail());
            supermarketRegistrationResponse.setEmailUrl(emailUrl);
            supermarketRegistrationResponse.setMessage(CHECK_YOUR_eMAIL_TO_CONFIRM_YOUR_SUPERMARKET_REGISTRATION);



        return supermarketRegistrationResponse;
    }

        @Override
    public AdminConfirmationResponse confirmNewSupermarketAdmin(String token) {

        String supermarketCode = token.substring(0, 5);
        String registeredUserEmail = token.substring(5);

        Supermarket supermarket = supermarketService.findSupermarketBySupermarketCode(supermarketCode);
        ShopCartifyUser foundUser = userService.findByEmail(registeredUserEmail);

        foundUser.getRoles().add(SUPERMARKET_ADMIN);

        ShopCartifyUser user = userService.updateSupermarketAdmin(foundUser);

        Supermarket updatedSupermarket = supermarketService.update(supermarket);

        return  mapAdminResponse(supermarket, foundUser);
    }

    @Override
    public AdminLoginResponse loginSupermarketAdmin(AdminLoginRequest adminLoginRequest) {
        var adminFound = userService.findByEmail(adminLoginRequest.getAdminEmail());

//        if (!passwordEncoder.matches(adminLoginRequest.getAdminPassword(), adminFound.getPassword())) {
//            throw new InvalidPasswordException(INVALID_CREDENTIALS_EXCEPTION.getMessage());
//        }
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        if (adminFound.getRoles().contains(SUPERMARKET_ADMIN)) {
            Supermarket supermarket = supermarketService.findSupermarketBySupermarketCode(adminLoginRequest.getSupermarketCode());


//           if (supermarket.getAdmins().contains(adminFound)) {

               adminLoginResponse.setMessage(LOGIN_SUCCESSFUL);
//           }
        }
        return adminLoginResponse;
    }

    private String sendUserEmail(String supermarketCode, ShopCartifyUser foundUser, String supermarketEmail) {
        String emailSubject = "Email Verification";
        String senderName = APP_NAME;
        String emailContent = getEmailContent(foundUser.getFirstName(), supermarketCode);
        if (foundUser.getEmail() != null && !foundUser.getEmail().isEmpty()) {



            emailService.sendEmail(supermarketEmail, emailSubject, emailContent);
            try {
                emailService.sendEmailForRegistration(foundUser.getEmail(), emailSubject, emailContent);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new ShopCartifyBaseException(e.getMessage());
            }
        }
        return  supermarketCode + foundUser.getEmail();
    }
        private String getEmailContent(String firstName, String code) {
            return "<p> Hi, " + firstName + ", </p>" +
                    "<p> Thank you for registering with us. " +
                    "Please use the code for future login and other needed activities on our platform: </p>" +
                    "<p> Your unique supermarket code is :  " + code + " </p>" +
                    "<p> Thank you <br> Users Registration Portal Service </p>";
        }

    @Override
    public Supermarket findSupermarketAdminByEmail(String email) {
        return null;
    }



    @Override
    public List<ShopCartifyUser> findAllSupermarketAdminById(List<Long> id) {
        return null;
    }



    private AdminConfirmationResponse mapAdminResponse(Supermarket supermarket, ShopCartifyUser foundUser) {
        AdminConfirmationResponse adminConfirmationResponse = new AdminConfirmationResponse();
        adminConfirmationResponse.setAdminEmail(foundUser.getEmail());
        adminConfirmationResponse.setAdminName(foundUser.getFirstName());
        adminConfirmationResponse.setSupermarketEmail(supermarket.getSupermarketEmail());
        adminConfirmationResponse.setSupermarketName(supermarket.getSupermarketName());
        adminConfirmationResponse.setSupermarketCode(supermarket.getSupermarketCode());
        adminConfirmationResponse.setMessage(YOU_ARE_NOW_AN_ADMINISTRATOR);
        return adminConfirmationResponse;
    }

}
