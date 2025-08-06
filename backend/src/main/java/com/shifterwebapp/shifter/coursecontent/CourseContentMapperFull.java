package com.shifterwebapp.shifter.coursecontent;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseContentMapperFull {

    CourseContentDtoFull toDto(CourseContent courseContent);
    List<CourseContentDtoFull> toDto(List<CourseContent> courseContents);

    @InheritInverseConfiguration
    CourseContent toEntity(CourseContentDtoFull courseContentDtoFull);
    @InheritInverseConfiguration
    List<CourseContent> toEntity(List<CourseContentDtoFull> courseContentDtoFulls);
}
