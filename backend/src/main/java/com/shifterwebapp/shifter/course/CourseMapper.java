package com.shifterwebapp.shifter.course;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);
    List<CourseDto> toDto(List<Course> courses);

    @InheritInverseConfiguration
    Course toEntity(CourseDto courseDto);
    @InheritInverseConfiguration
    List<Course> toEntity(List<CourseDto> courseDtos);
}
