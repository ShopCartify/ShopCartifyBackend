package com.shopcartify.services.implementations.adminServices;

import com.shopcartify.enums.UserRole;
import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.model.Supermarket;
import com.shopcartify.repositories.UserRepository;
import com.shopcartify.services.interfaces.adminServices.SuperAdminService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.shopcartify.enums.UserRole.SUPER_ADMIN;


@Service
@AllArgsConstructor
public class ShopCartifySuperAdminService implements SuperAdminService {
    public final UserRepository userRepository;
    private final JavaMailSender emailSender;

    @Override
    public Supermarket findSuperAdminByEmail(String email) {
        return null;
    }

    @Override
    public List<ShopCartifyUser> findAllSuperAdminById(List<Long> id) {
        return getShopCartifyUsers(userRepository, SUPER_ADMIN);
    }

    static List<ShopCartifyUser> getShopCartifyUsers(UserRepository userRepository, UserRole userRole) {
        List<ShopCartifyUser> allAdmins = userRepository.findAll();
        List<ShopCartifyUser> superAdmins = new ArrayList<>();

        for (ShopCartifyUser allAdmin : allAdmins) {
            if (allAdmin.getRoles().contains(userRole)) {
                superAdmins.add(allAdmin);
            }
        }
        return superAdmins;
    }

    @Override
    public ShopCartifyUser inviteAdmin(String email, UserRole role) {
        Optional<ShopCartifyUser> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            ShopCartifyUser user = existingUser.get();
            user.getRoles().add(role);
            userRepository.save(user);
            return user;
        } else {
            ShopCartifyUser newUser = new ShopCartifyUser();
            newUser.setEmail(email);
            newUser.setPassword("defaultPassword");
            newUser.setRoles(Collections.singleton(role));
            userRepository.save(newUser);

            sendInvitationEmail(email, role);

            return newUser;
        }
    }

    private void sendInvitationEmail(String email, UserRole role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Invitation to ShopCartify Admin");

        if (role == UserRole.CHECKOUT_ADMIN) {
            message.setText("You have been invited as a Checkout Admin. Please use the provided default password to log in and update your account.");
        } else if (role == UserRole.SUPERMARKET_ADMIN) {
            message.setText("You have been invited as a Supermarket Admin. Please use the provided default password to log in and update your account.");
        }

        emailSender.send(message);
    }
}



