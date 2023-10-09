package com.shopcartify.services.implementations.adminServicestest;

import com.shopcartify.dto.reqests.AdminLoginRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.dto.responses.AdminConfirmationResponse;
import com.shopcartify.dto.responses.AdminLoginResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.enums.UserRole;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.services.interfaces.UserService;
import com.shopcartify.services.interfaces.adminServices.SupermarketAdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.shopcartify.enums.UserRole.SUPERMARKET_ADMIN;
import static com.shopcartify.utils.Constants.LOGIN_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class SupermarketAdminTest {

    @Autowired
    private SupermarketAdminService supermarketAdminService;
    @Autowired
    UserService userService;

    ShopCartifyUser registeredUser;

    @BeforeEach
    public void setUp(){
        registeredUser = registerUser();
    }

    @Test
    public void testSupermarketAdminCanRegister(){
        String superMarketName = "Landol Supermarket";
        SupermarketRegistrationRequest supermarketRegistrationRequest = createSupermarketRegistrationRequest(superMarketName);
        SupermarketRegistrationResponse supermarketRegistrationResponse =
                supermarketAdminService.registerSupermarketAdmin(supermarketRegistrationRequest);

        assertEquals(supermarketRegistrationResponse.getSupermarketName(), superMarketName);
        assertEquals(supermarketRegistrationResponse.getSupermarketEmail(), supermarketRegistrationRequest.getSupermarketEmail());

        ShopCartifyUser foundUser = userService.findByEmail(registeredUser.getEmail());

        assertTrue(verifyUserIsNotYetSupermarketAdmin(foundUser));

    }
    @Test
    public void testSupermarketAdminCanRegisterAndConfirmNewRole(){
        String superMarketName = "Delly Stores";
        var supermarketRegistrationRequest = createSupermarketRegistrationRequest(superMarketName);
        SupermarketRegistrationResponse supermarketRegistrationResponse =
                supermarketAdminService.registerSupermarketAdmin(supermarketRegistrationRequest);

        assertEquals(supermarketRegistrationResponse.getSupermarketName(), superMarketName);
        assertEquals(supermarketRegistrationResponse.getSupermarketEmail(), supermarketRegistrationRequest.getSupermarketEmail());

        ShopCartifyUser foundUser = userService.findByEmail(registeredUser.getEmail());
        assertTrue(verifyUserIsNotYetSupermarketAdmin(foundUser));
        String emailUrl =  supermarketRegistrationResponse.getSupermarketCode() + supermarketRegistrationResponse.getEmailOfRegisteredUser() ;
        AdminConfirmationResponse adminConfirmationResponse =supermarketAdminService.confirmNewSupermarketAdmin(emailUrl);

        assertEquals(adminConfirmationResponse.getSupermarketName(), superMarketName);
        ShopCartifyUser foundAdmin = userService.findByEmail(registeredUser.getEmail());


        assertFalse(verifyUserIsNotYetSupermarketAdmin(foundAdmin));
    }
    @Test
    public void supermarketAdminCanLoginTest(){
        String superMarketName = "Gold Store";
        var supermarketRegistrationRequest = createSupermarketRegistrationRequest(superMarketName);
        SupermarketRegistrationResponse supermarketRegistrationResponse =
                supermarketAdminService.registerSupermarketAdmin(supermarketRegistrationRequest);

        assertEquals(supermarketRegistrationResponse.getSupermarketName(), superMarketName);
        assertEquals(supermarketRegistrationResponse.getSupermarketEmail(), supermarketRegistrationRequest.getSupermarketEmail());

        ShopCartifyUser foundUser = userService.findByEmail(registeredUser.getEmail());
        assertTrue(verifyUserIsNotYetSupermarketAdmin(foundUser));
        String emailUrl =  supermarketRegistrationResponse.getSupermarketCode() + supermarketRegistrationResponse.getEmailOfRegisteredUser() ;
        AdminConfirmationResponse adminConfirmationResponse =supermarketAdminService.confirmNewSupermarketAdmin(emailUrl);

        assertEquals(adminConfirmationResponse.getSupermarketName(), superMarketName);
        ShopCartifyUser foundAdmin = userService.findByEmail(registeredUser.getEmail());

        assertFalse(verifyUserIsNotYetSupermarketAdmin(foundAdmin));
        AdminLoginRequest adminLoginRequest = createAdminLoginRequest(foundAdmin, supermarketRegistrationResponse.getSupermarketCode() );
        AdminLoginResponse adminLoginResponse = supermarketAdminService.loginSupermarketAdmin(adminLoginRequest);

        assertEquals(LOGIN_SUCCESSFUL, adminLoginResponse.getMessage());
    }

    @Test
    public void supermarketAdminCanInviteCheckoutAdmin(){

    }
    private AdminLoginRequest createAdminLoginRequest(ShopCartifyUser foundAdmin, String supermarketCode) {
        AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
        adminLoginRequest.setSupermarketCode(supermarketCode);
        adminLoginRequest.setAdminEmail(foundAdmin.getEmail());
        adminLoginRequest.setAdminPassword(foundAdmin.getPassword());
        return adminLoginRequest;
    }

    private boolean verifyUserIsNotYetSupermarketAdmin(ShopCartifyUser foundUser) {

        Set<UserRole> roles = foundUser.getRoles();

        var isAdmin = true;

        for (var role : roles) {
            if(Objects.equals(role, SUPERMARKET_ADMIN)){
                isAdmin = false;
                break;
            }
        }
        return isAdmin;
    }

    @Test
    public void supermarketAdminCanAddProductTest(){

    }

    private SupermarketRegistrationRequest createSupermarketRegistrationRequest(String superMarketName) {
        return  SupermarketRegistrationRequest.builder()
                .supermarketEmail(registeredUser.getEmail())
                .registeredUserId(registeredUser.getUserId())
                .supermarketName(superMarketName)
//                .supermarketCAC("supermarket cac")
                .supermarketLocation("supermarket location")
                .supermarketEmail("supermarketemail@supermarket.com")
                .cacUrl("cacurl://supermarket")
                .build();
    }
    private ShopCartifyUser registerUser() {
        UserRegistrationRequest userRegistrationRequest = createUserRegistrationRequest();

        Optional<ShopCartifyUser> foundUser = userService.findOptionalUserByEmail(userRegistrationRequest.getEmail());
        if (foundUser.isEmpty()){
             userService.registerUser(userRegistrationRequest);
            foundUser = userService.findOptionalUserByEmail(userRegistrationRequest.getEmail());
        }
        var user =foundUser.get();
        if (user.getRoles().contains(SUPERMARKET_ADMIN)){
            user.getRoles().remove(SUPERMARKET_ADMIN);
            userService.updateSupermarketAdmin(user);
        }
        return user;
}

    private UserRegistrationRequest createUserRegistrationRequest() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("useremail@gmail.com");
        userRegistrationRequest.setFirstName("Johnny");
        userRegistrationRequest.setLastName("Jack");
        userRegistrationRequest.setPassword("password");

        return userRegistrationRequest;
    }

    private void createSupermarketAdmin(){

    }
}
