package com.shifterwebapp.shifter.courselecture;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseLectureMapperPreview {

    CourseLecturePreviewDto toDto(CourseLecture courseContent);
    List<CourseLecturePreviewDto> toDto(List<CourseLecture> courseContents);

    @InheritInverseConfiguration
    CourseLecture toEntity(CourseLecturePreviewDto courseContentDto);
    @InheritInverseConfiguration
    List<CourseLecture> toEntity(List<CourseLecturePreviewDto> courseContentDtos);
}
