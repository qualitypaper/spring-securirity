package com.qualitypaper.co_tourism.repository;

import com.qualitypaper.co_tourism.model.tokens.confirmationToken.ConfirmationToken;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer>{

    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
