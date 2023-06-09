package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.model.tokens.TokenType;
import com.qualitypaper.co_tourism.model.tokens.authenticationToken.AuthenticationToken;
import com.qualitypaper.co_tourism.model.tokens.confirmationToken.ConfirmationToken;
import com.qualitypaper.co_tourism.model.tokens.forgotPassword.ForgotPassword;
import com.qualitypaper.co_tourism.model.user.User;
import com.qualitypaper.co_tourism.repository.AuthenticationTokenRepository;
import com.qualitypaper.co_tourism.repository.ConfirmationTokenRepository;
import com.qualitypaper.co_tourism.repository.ForgotPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TokensService {

    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;

    void saveUserAuthenticationToken(User user, String jwtToken) {
        var token = AuthenticationToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .created(LocalDateTime.now())
                .build();
        authenticationTokenRepository.save(token);
    }
    void saveUserForgotPasswordToken(User user, String forgotPasswordToken) {
        var token = ForgotPassword.builder()
                .user(user)
                .token(forgotPasswordToken)
                .tokenType(TokenType.FORGOT_PASSWORD)
                .expires(LocalDateTime.now().plusMinutes(15))
                .created(LocalDateTime.now())
                .build();
        forgotPasswordRepository.save(token);
    }

    void saveUserConfirmationToken(User user, String confirmationToken) {
        var token = ConfirmationToken.builder()
                .user(user)
                .confirmationToken(confirmationToken)
                .tokenType(TokenType.CONFIRMATION)
                .expires(LocalDateTime.now().plusMinutes(15))
                .created(LocalDateTime.now())
                .build();
        confirmationTokenRepository.save(token);
    }

    void revokeAllUserTokens(User user) {
        var validUserTokens = authenticationTokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(authenticationToken -> {
            authenticationToken.setExpired(true);
            authenticationToken.setRevoked(true);
        });
        authenticationTokenRepository.saveAll(validUserTokens);
    }

    public String generateConfirmationToken() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 30;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

       return generatedString;
    }


    public boolean checkConfirmationTokenExpiration(ConfirmationToken confirmationToken){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expires = confirmationToken.getExpires();
        Duration difference = Duration.between(now, expires);
        return Math.abs(difference.toMinutes()) < 15;
    }


}
