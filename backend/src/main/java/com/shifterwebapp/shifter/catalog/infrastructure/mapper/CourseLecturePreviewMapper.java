//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoPreview;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface CourseLectureMapperPreview {
//
//    CourseLectureDtoPreview toDto(CourseLecture courseContent);
//    List<CourseLectureDtoPreview> toDto(List<CourseLecture> courseContents);
//
//    @InheritInverseConfiguration
//    CourseLecture toEntity(CourseLectureDtoPreview courseContentDto);
//    @InheritInverseConfiguration
//    List<CourseLecture> toEntity(List<CourseLectureDtoPreview> courseContentDtos);
//}
