package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.model.tokens.TokenType;
import com.qualitypaper.co_tourism.repository.ConfirmationTokenRepository;
import com.qualitypaper.co_tourism.repository.UserRepository;
import com.qualitypaper.co_tourism.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${confirmation.url}")
    private String confirmationUrl;
    @Value("${forgot-password.url}")
    private String forgotPasswordUrl;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final TokensService tokensService;
    private final JwtService jwtService;
    private final EmailService emailService;

    public boolean checkLogin(String token) {
        var user = userRepository.findByEmail(jwtService.extractUsername(token)).get();
        return jwtService.isTokenValid(token, user);
    }

    public String updateAuthenticationToken(String jwtToken) {
        var user = userRepository.findByEmail(jwtService.extractUsername(jwtToken)).get();
        var newToken = jwtService.generateToken(user, user.getId());
        tokensService.saveUserAuthenticationToken(user, newToken);
        return newToken;
    }

    public void confirmUser(String token) {
        var confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        if (!tokensService.checkConfirmationTokenExpiration(confirmationToken))
            throw new IllegalStateException("Token expired");
        var user = confirmationToken.getUser();
        user.setConfirmed(true);
        userRepository.save(user);
    }

    public String generateNewTokenAndSendEmail(TokenType type, String jwtToken) {
        String userEmail = jwtService.extractUsername(jwtToken);
        if (type == TokenType.CONFIRMATION) {
            String confirmationToken = getNewConfirmationToken(jwtToken);
            emailService.sendSimpleMessage(userEmail, "Confirm your account", confirmationUrl + confirmationToken);
            return confirmationToken;
        } else if (type == TokenType.FORGOT_PASSWORD) {
            String forgotPasswordToken = getNewForgotPasswordToken(jwtToken);
            emailService.sendSimpleMessage(userEmail, "Reset your password", forgotPasswordUrl + forgotPasswordToken);
            return forgotPasswordToken;
        } else throw new IllegalStateException("Invalid token type");
    }

    private String getNewConfirmationToken(String jwtToken) {
        if (!checkLogin(jwtToken)) throw new IllegalStateException("User not logged in");
        var userEmail = jwtService.extractUsername(jwtToken);
        var user = userRepository.findByEmail(userEmail).get();
        var confirmationToken = tokensService.generateConfirmationToken();
        tokensService.saveUserConfirmationToken(user, confirmationToken);
        return confirmationToken;
    }

    private String getNewForgotPasswordToken(String jwtToken) {
        if (!checkLogin(jwtToken)) throw new IllegalStateException("User not logged in");
        var userEmail = jwtService.extractUsername(jwtToken);
        var user = userRepository.findByEmail(userEmail).get();
        var forgotPasswordToken = tokensService.generateConfirmationToken();
        tokensService.saveUserForgotPasswordToken(user, forgotPasswordToken);
        return forgotPasswordToken;
    }


}
