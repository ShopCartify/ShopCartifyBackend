package com.shopcartify.dto.responses;

import com.shopcartify.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInviteResponse {
    private String message;
    private UserRole adminRole;

}
