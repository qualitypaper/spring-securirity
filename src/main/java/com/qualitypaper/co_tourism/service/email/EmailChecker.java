package com.qualitypaper.co_tourism.service.email;

public interface EmailChecker {

    default boolean isValid(String email){
        if(email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) return true;
        return false;
    }
}
