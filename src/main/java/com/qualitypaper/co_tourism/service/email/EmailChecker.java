package com.qualitypaper.co_tourism.service.email;

import org.springframework.stereotype.Component;

@Component
public interface EmailChecker {

    static boolean isValid(String email){
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
