package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseContentMapper {

    CourseContentDto toDto(CourseContent courseContent);
    List<CourseContentDto> toDto(List<CourseContent> courseContents);

    @InheritInverseConfiguration
    CourseContent toEntity(CourseContentDto courseContentDto);
    @InheritInverseConfiguration
    List<CourseContent> toEntity(List<CourseContentDto> courseContentDtos);
}
