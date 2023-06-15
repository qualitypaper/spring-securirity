package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.model.token.TokenRepository;
import com.qualitypaper.co_tourism.model.user.User;
import com.qualitypaper.co_tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public boolean checkLogin(String token) {
        User user = userRepository.findByEmail(jwtService.extractUsername(token)).get();
        return jwtService.isTokenValid(token, user);
    }

    public String updateToken(String token) {
        User user = userRepository.findByEmail(jwtService.extractUsername(token)).get();
        return jwtService.generateToken(user, user.getId());
    }
}
