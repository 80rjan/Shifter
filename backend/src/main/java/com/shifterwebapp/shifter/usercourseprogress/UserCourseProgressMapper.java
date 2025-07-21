package com.shifterwebapp.shifter.usercourseprogress;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCourseProgressMapper {

    UserCourseProgressDto toDto(UserCourseProgress userCourseProgress);
    List<UserCourseProgressDto> toDto(List<UserCourseProgress> userCourseProgress);

    @InheritInverseConfiguration
    UserCourseProgress toEntity(UserCourseProgressDto userCourseProgressDto);
    @InheritInverseConfiguration
    List<UserCourseProgress> toEntity(List<UserCourseProgressDto> userCourseProgressDtos);
}
