package com.shifterwebapp.shifter.coursecontent;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseContentMapperPreview {

    CourseContentDtoPreview toDto(CourseContent courseContent);
    List<CourseContentDtoPreview> toDto(List<CourseContent> courseContents);

    @InheritInverseConfiguration
    CourseContent toEntity(CourseContentDtoPreview courseContentDtoPreview);
    @InheritInverseConfiguration
    List<CourseContent> toEntity(List<CourseContentDtoPreview> courseContentDtoPreviews);
}
