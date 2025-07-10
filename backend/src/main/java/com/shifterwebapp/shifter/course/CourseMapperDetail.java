package com.shifterwebapp.shifter.course;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapperDetail {

    CourseDtoDetail toDto(Course course);
    List<CourseDtoDetail> toDto(List<Course> courses);

    @InheritInverseConfiguration
    Course toEntity(CourseDtoDetail courseDto);
    @InheritInverseConfiguration
    List<Course> toEntity(List<CourseDtoDetail> courseDtos);
}
