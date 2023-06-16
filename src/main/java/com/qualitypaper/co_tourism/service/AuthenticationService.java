package com.qualitypaper.co_tourism.service;

import com.qualitypaper.co_tourism.controller.dto.AuthenticationRequest;
import com.qualitypaper.co_tourism.controller.dto.AuthenticationResponse;
import com.qualitypaper.co_tourism.controller.dto.RegisterRequest;
import com.qualitypaper.co_tourism.exceptions.EmailNotValidException;
import com.qualitypaper.co_tourism.mappers.user.UserMapperImplementation;
import com.qualitypaper.co_tourism.model.user.Role;
import com.qualitypaper.co_tourism.model.user.User;
import com.qualitypaper.co_tourism.repository.UserRepository;
import com.qualitypaper.co_tourism.service.email.EmailChecker;
import com.qualitypaper.co_tourism.service.email.EmailServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokensService tokensService;
    private final UserMapperImplementation userMapperImplementation;
    private final EmailServiceImplementation emailServiceImplementation;
    private final EmailChecker emailChecker;


    public AuthenticationResponse register(RegisterRequest request) {
        if(!emailChecker.isValid(request.getEmail())) throw new EmailNotValidException("Email is not valid");
        var user = mapUser(request);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user, user.getId());
        var confirmationToken = tokensService.generateConfirmationToken();
        tokensService.saveUserAuthenticationToken(savedUser, jwtToken);
        tokensService.saveUserConfirmationToken(user, confirmationToken);
        emailServiceImplementation.sendSimpleMessage(user.getEmail(), "Confirm your account", "http://localhost:8080/api/auth/confirm?token=" + confirmationToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if(Objects.isNull(request.getPassword()) || Objects.isNull(request.getEmail())) return null;
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).get();
        var jwtToken = jwtService.generateToken(user, user.getId());
        tokensService.revokeAllUserTokens(user);
        tokensService.saveUserAuthenticationToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    private User mapUser(RegisterRequest request) {
        var user = userMapperImplementation.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setBanned(false);
        user.setConfirmed(false);
        user.setReviews(Collections.emptyList());
        return user;
    }


}
