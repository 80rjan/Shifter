package com.shifterwebapp.shifter.course.mapper;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapperPreview {

    @Mapping(target = "courseContentCount", expression = "java(course.getCourseContents() != null ? course.getCourseContents().size() : 0)")
    CourseDtoPreview toDto(Course course);
    List<CourseDtoPreview> toDto(List<Course> courses);

    @InheritInverseConfiguration
    Course toEntity(CourseDtoPreview courseDto);
    @InheritInverseConfiguration
    List<Course> toEntity(List<CourseDtoPreview> courseDtos);
}
