//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.web.response.LectureProgressDto;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface LectureProgressMapper {
//
//    LectureProgressDto toDto(LectureProgress lectureProgress);
//    List<LectureProgressDto> toDto(List<LectureProgress> lectureProgresses);
//
//    @InheritInverseConfiguration
//    LectureProgress toEntity(LectureProgressDto lectureProgressDto);
//    @InheritInverseConfiguration
//    List<LectureProgress> toEntity(List<LectureProgressDto> lectureProgressDtos);
//}
