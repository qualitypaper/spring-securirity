package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.controller.dto.AuthenticationRequest;
import com.qualitypaper.co_tourism.controller.dto.AuthenticationResponse;
import com.qualitypaper.co_tourism.controller.dto.RegisterRequest;
import com.qualitypaper.co_tourism.mappers.user.UserMapperImplementation;
import com.qualitypaper.co_tourism.model.token.Token;
import com.qualitypaper.co_tourism.model.token.TokenRepository;
import com.qualitypaper.co_tourism.model.token.TokenType;
import com.qualitypaper.co_tourism.model.user.Role;
import com.qualitypaper.co_tourism.model.user.User;
import com.qualitypaper.co_tourism.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapperImplementation userMapperImplementation;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = mapUser(request);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user, user.getId());
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if(Objects.isNull(request.getPassword()) || Objects.isNull(request.getEmail())) return null;
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).get();
        var jwtToken = jwtService.generateToken(user, user.getId());
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .lastLogin(LocalDateTime.now())
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private User mapUser(RegisterRequest request) {
        var user = userMapperImplementation.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        return user;
    }
}
