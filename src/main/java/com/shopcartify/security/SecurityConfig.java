package com.shopcartify.security;

import com.shopcartify.security.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.shopcartify.enums.UserRole.ADMIN;
import static com.shopcartify.enums.UserRole.CUSTOMER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/user/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/supermarketAdminController/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/productController/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/cartProduct/**").permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/cart/**").permitAll())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
