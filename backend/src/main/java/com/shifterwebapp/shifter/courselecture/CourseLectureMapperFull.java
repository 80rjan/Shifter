package com.shifterwebapp.shifter.courselecture;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseLectureMapperFull {

    CourseLectureFullDto toDto(CourseLecture courseContent);
    List<CourseLectureFullDto> toDto(List<CourseLecture> courseContents);

    @InheritInverseConfiguration
    CourseLecture toEntity(CourseLectureFullDto courseContentDto);
    @InheritInverseConfiguration
    List<CourseLecture> toEntity(List<CourseLectureFullDto> courseContentDtos);
}
