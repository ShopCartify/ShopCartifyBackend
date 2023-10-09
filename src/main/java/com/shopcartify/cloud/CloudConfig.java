package com.shopcartify.cloud;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class CloudConfig {

        @Value("${cloud.api.secret}")
        private String cloudSecret;

        @Value("${cloud.api.key}")
        private String cloudKey;

        @Value("${cloud.api.name}")
        private String cloudName;

    }
