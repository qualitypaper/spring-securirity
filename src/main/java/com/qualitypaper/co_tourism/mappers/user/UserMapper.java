package com.qualitypaper.co_tourism.mappers.user;

import com.qualitypaper.co_tourism.controller.dto.RegisterRequest;
import com.qualitypaper.co_tourism.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToUser(RegisterRequest request);

}
