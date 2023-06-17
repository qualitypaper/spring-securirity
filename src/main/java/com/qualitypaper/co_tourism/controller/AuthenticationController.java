package com.qualitypaper.co_tourism.controller;

import com.qualitypaper.co_tourism.controller.dto.AuthenticationRequest;
import com.qualitypaper.co_tourism.controller.dto.AuthenticationResponse;
import com.qualitypaper.co_tourism.controller.dto.RegisterRequest;
import com.qualitypaper.co_tourism.repository.UserRepository;
import com.qualitypaper.co_tourism.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
