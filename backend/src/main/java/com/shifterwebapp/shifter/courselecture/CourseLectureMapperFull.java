package com.shifterwebapp.shifter.courselecture;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseLectureMapperFull {

    CourseLectureDtoFull toDto(CourseLecture courseContent);
    List<CourseLectureDtoFull> toDto(List<CourseLecture> courseContents);

    @InheritInverseConfiguration
    CourseLecture toEntity(CourseLectureDtoFull courseContentDto);
    @InheritInverseConfiguration
    List<CourseLecture> toEntity(List<CourseLectureDtoFull> courseContentDtos);
}
