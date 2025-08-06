package com.shifterwebapp.shifter.course.mapper;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

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
