package com.qualitypaper.co_tourism.repository;

import com.qualitypaper.co_tourism.model.tokens.forgotPassword.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    ForgotPassword findForgotPasswordByToken(String token);
}
