package com.shifterwebapp.shifter.course;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapperPreview {

    CourseDtoPreview toDto(Course course);
    List<CourseDtoPreview> toDto(List<Course> courses);

    @InheritInverseConfiguration
    Course toEntity(CourseDtoPreview courseDto);
    @InheritInverseConfiguration
    List<Course> toEntity(List<CourseDtoPreview> courseDtos);
}
