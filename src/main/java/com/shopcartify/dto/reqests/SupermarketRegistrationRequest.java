package com.shopcartify.dto.reqests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SupermarketRegistrationRequest {
    private String supermarketName;
    private String supermarketEmail;
    private String cacUrl;
    private Long registeredUserId;

    private String supermarketLocation;
    private String supermarketCode;
}
