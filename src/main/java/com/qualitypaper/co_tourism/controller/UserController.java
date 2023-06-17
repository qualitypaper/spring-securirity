package com.qualitypaper.co_tourism.controller;

import com.qualitypaper.co_tourism.model.tokens.TokenType;
import com.qualitypaper.co_tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;


    @GetMapping("/checkLogin/")
    public boolean checkLogin(@RequestHeader("Authorization") String token) {
        return userService.checkLogin(token);
    }

    @GetMapping("/updateAuthenticationToken/")
    public String updateToken(@RequestHeader("Authorization") String token) {
        return userService.updateAuthenticationToken(token);
    }

    @GetMapping("/getNewConfirmationToken/")
    public String getNewConfirmationToken(@RequestHeader("Authorization") String token) {
        return userService.generateNewTokenAndSendEmail(TokenType.CONFIRMATION, token);
    }

    @GetMapping("/getNewForgotPasswordToken/")
    public String getNewForgotPasswordToken(@RequestHeader("Authorization") String token) {
        return userService.generateNewTokenAndSendEmail(TokenType.FORGOT_PASSWORD, token);
    }
}
