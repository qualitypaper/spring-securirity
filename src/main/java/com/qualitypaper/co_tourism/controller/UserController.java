package com.qualitypaper.co_tourism.controller;

import com.qualitypaper.co_tourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;


    @GetMapping("/checkLogin/{token}")
    public boolean checkLogin(@PathVariable String token){
        return userService.checkLogin(token);
    }

    @GetMapping("/updateToken/{token}")
    public String updateToken(@PathVariable String token){
        return userService.updateToken(token);
    }
}
