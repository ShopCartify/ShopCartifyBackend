package com.shopcartify.repositories;

import com.shopcartify.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminEmail(String adminEmail);
}
