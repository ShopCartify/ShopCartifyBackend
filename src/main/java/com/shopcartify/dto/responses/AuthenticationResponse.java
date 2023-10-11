package com.shopcartify.dto.responses;

import com.shopcartify.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private Long Id;
    private String token;
    private String message;
    private UserRole role;
    private String email;
    public AuthenticationResponse(String message) {
    }
}
