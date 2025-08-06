package com.shifterwebapp.shifter.courselecture;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseLectureMapperPreview {

    CourseLectureDtoPreview toDto(CourseLecture courseContent);
    List<CourseLectureDtoPreview> toDto(List<CourseLecture> courseContents);

    @InheritInverseConfiguration
    CourseLecture toEntity(CourseLectureDtoPreview courseContentDto);
    @InheritInverseConfiguration
    List<CourseLecture> toEntity(List<CourseLectureDtoPreview> courseContentDtos);
}
