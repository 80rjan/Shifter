package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoLearn;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseLectureMapperFull {

    CourseLectureDtoLearn toDto(CourseLecture courseContent);
    List<CourseLectureDtoLearn> toDto(List<CourseLecture> courseContents);

    @InheritInverseConfiguration
    CourseLecture toEntity(CourseLectureDtoLearn courseContentDto);
    @InheritInverseConfiguration
    List<CourseLecture> toEntity(List<CourseLectureDtoLearn> courseContentDtos);
}
