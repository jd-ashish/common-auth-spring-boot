package com.projects.app.services.mapper;

import com.projects.app.dto.UserProfileDTO;
import com.projects.app.models.User;
import com.projects.app.dto.UserDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User>{

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
