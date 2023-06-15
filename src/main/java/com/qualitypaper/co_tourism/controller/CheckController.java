package com.qualitypaper.co_tourism.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {


    @GetMapping("/loshara")
    public String check() {
        return "OK";
    }
}
