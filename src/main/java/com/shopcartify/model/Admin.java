package com.shopcartify.model;

import com.shopcartify.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adminName;
    private String adminEmail;
    private String adminCode;
    private Set<UserRole> roles;

    @ManyToOne
    @JoinColumn(name = "supermarket_id")
    private Supermarket supermarket;

    }


