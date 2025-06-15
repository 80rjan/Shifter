package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
    @InheritInverseConfiguration
    List<User> toEntity(List<UserDto> userDtos);
}
