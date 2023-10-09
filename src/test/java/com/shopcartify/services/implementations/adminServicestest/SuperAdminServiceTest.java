package com.shopcartify.services.implementations.adminServicestest;

import com.shopcartify.dto.reqests.UserRegistrationRequest;
import com.shopcartify.enums.UserRole;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.services.implementations.adminServices.ShopCartifySuperAdminService;
import com.shopcartify.services.interfaces.adminServices.SuperAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class SuperAdminServiceTest {


    private ShopCartifyUser superAdmin ;

    @Autowired
    private SuperAdminService superAdminService;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;


    @BeforeEach
    public void setUp(){
        superAdmin = createSuperAdmin();
        userRepository = mock(UserRepository.class);
        mailSender = mock(JavaMailSender.class);
        superAdminService = new ShopCartifySuperAdminService(userRepository, mailSender);

    }

    private void registerUser(){

    }
    private static UserRegistrationRequest buildUserRegistration(){
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setFirstName("Favour");
        registrationRequest.setLastName("Chiemela");
        registrationRequest.setPassword("2233");
        registrationRequest.setEmail("favourChi@gmail.com");
        return registrationRequest;
    }




    private ShopCartifyUser createSuperAdmin(){
        ShopCartifyUser superAdmin = ShopCartifyUser.builder()
                .email("superadmin@example.com")
//                .role(SUPER_ADMIN)
                .password("P@ssw0rd")
                .firstName("Super Admin")
                .lastName("name")
                .build();
        Optional<ShopCartifyUser> foundSuperAdmin = userRepository.findByEmail(superAdmin.getEmail());
        return foundSuperAdmin.orElseGet(() -> userRepository.save(superAdmin));
    }

    @Test
    void testInviteAdmin_ExistingUser() {
        String email = "existing@example.com";
        UserRole role = UserRole.CHECKOUT_ADMIN;

        ShopCartifyUser existingUser = new ShopCartifyUser();
        existingUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        ShopCartifyUser invitedUser = superAdminService.inviteAdmin(email, role);

        assertNotNull(invitedUser);
        assertEquals(1, invitedUser.getRoles().size());
        assertTrue(invitedUser.getRoles().contains(role));

        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testInviteAdmin_NewUser() {
        String email = "new@example.com";
        UserRole role = UserRole.SUPERMARKET_ADMIN;

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ShopCartifyUser invitedUser = superAdminService.inviteAdmin(email, role);

        assertNotNull(invitedUser);
        assertEquals(email, invitedUser.getEmail());
        assertEquals(1, invitedUser.getRoles().size());
        assertTrue(invitedUser.getRoles().contains(role));
        assertEquals("defaultPassword", invitedUser.getPassword());

        verify(userRepository, times(1)).save(invitedUser);
    }
}
