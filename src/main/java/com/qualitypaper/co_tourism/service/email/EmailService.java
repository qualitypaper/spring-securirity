package com.qualitypaper.co_tourism.service.email;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
