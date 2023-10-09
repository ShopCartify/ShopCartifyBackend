package com.shopcartify.controller;

import com.shopcartify.dto.responses.AdminInviteResponse;
import com.shopcartify.enums.UserRole;
import com.shopcartify.services.interfaces.SupermarketService;
import com.shopcartify.services.interfaces.adminServices.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SupermarketService supermarketService;

    @PostMapping("/invite")
    public ResponseEntity<AdminInviteResponse> inviteAdmin(
            @RequestParam String supermarketCode,
            @RequestParam String adminEmail,
            @RequestParam UserRole adminRole
    ) {
        if (adminRole != UserRole.CHECKOUT_ADMIN && adminRole != UserRole.SUPERMARKET_ADMIN) {
            return ResponseEntity.badRequest().build();
        }
        supermarketService.inviteAdminToSupermarket(supermarketCode, adminEmail, adminRole);

        AdminInviteResponse response = new AdminInviteResponse(
                "Admin invitation sent successfully.",
                adminRole
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
