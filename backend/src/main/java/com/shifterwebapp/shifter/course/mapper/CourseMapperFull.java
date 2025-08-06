package com.shifterwebapp.shifter.course.mapper;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapperFull {

    CourseDtoFull toDto(Course course);
    List<CourseDtoFull> toDto(List<Course> courses);

    @InheritInverseConfiguration
    Course toEntity(CourseDtoFull courseDto);
    @InheritInverseConfiguration
    List<Course> toEntity(List<CourseDtoFull> courseDtos);
}
