package com.projects.app.services.mapper;

import com.projects.app.dto.RoleDto;
import com.projects.app.dto.UserDto;
import com.projects.app.models.Role;
import com.projects.app.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role user);

    Role toEntity(RoleDto roleDto);

}
