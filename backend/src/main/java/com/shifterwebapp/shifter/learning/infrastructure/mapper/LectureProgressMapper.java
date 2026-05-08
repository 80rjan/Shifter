//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.web.response.LectureProgressResponse;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface LectureProgressMapper {
//
//    LectureProgressResponse toDto(LectureProgress lectureProgress);
//    List<LectureProgressResponse> toDto(List<LectureProgress> lectureProgresses);
//
//    @InheritInverseConfiguration
//    LectureProgress toEntity(LectureProgressResponse lectureProgressDto);
//    @InheritInverseConfiguration
//    List<LectureProgress> toEntity(List<LectureProgressResponse> lectureProgressDtos);
//}
