package com.shopcartify.model;

import com.shopcartify.model.Admin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supermarkets")
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supermarketName;

    private String supermarketEmail;

    private String supermarketCode;
    private String cacUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "supermarket_admins",
            joinColumns = @JoinColumn(name = "supermarket_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id")
    )

    private Set<Admin> admins = new HashSet<>();

}
