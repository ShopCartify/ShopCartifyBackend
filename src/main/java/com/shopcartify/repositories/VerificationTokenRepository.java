package com.shopcartify.repositories;

import com.shopcartify.model.ShopCartifyUser;
import com.shopcartify.registration.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(ShopCartifyUser user);
}
