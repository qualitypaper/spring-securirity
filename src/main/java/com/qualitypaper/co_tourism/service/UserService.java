package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.repository.ConfirmationTokenRepository;
import com.qualitypaper.co_tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final TokensService tokensService;
    private final JwtService jwtService;

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

    public String getNewConfirmationToken(String jwtToken){
        var userEmail = jwtService.extractUsername(jwtToken);
        var user = userRepository.findByEmail(userEmail).get();
        var confirmationToken = tokensService.generateConfirmationToken();
        tokensService.saveUserConfirmationToken(user, confirmationToken);
        return confirmationToken;
    }

    public void confirmUser(String token){
        var confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        var user = confirmationToken.getUser();
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
