package com.shifterwebapp.shifter.user.mapper;

import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.dto.UserDto;
import com.shifterwebapp.shifter.user.dto.UserDtoAuth;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);

    UserDtoAuth toDtoAuth(User user);
    List<UserDtoAuth> toDtoAuth(List<User> users);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
    @InheritInverseConfiguration
    List<User> toEntity(List<UserDto> userDtos);

    @InheritInverseConfiguration
    User toEntityAuth(UserDtoAuth userDtoAuth);
    @InheritInverseConfiguration
    List<User> toEntityAuth(List<UserDtoAuth> userDtoAuths);
}
