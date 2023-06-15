package com.qualitypaper.co_tourism.mappers.user;

import com.qualitypaper.co_tourism.controller.dto.RegisterRequest;
import com.qualitypaper.co_tourism.mappers.user.UserMapper;
import com.qualitypaper.co_tourism.model.user.User;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-19T22:03:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
public class UserMapperImplementation implements UserMapper {


    @Override
    public User mapToUser(RegisterRequest request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return user;
    }

}
