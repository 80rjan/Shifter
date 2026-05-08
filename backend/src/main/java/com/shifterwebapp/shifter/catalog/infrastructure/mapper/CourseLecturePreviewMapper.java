//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLecturePreviewResponse;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface CourseLectureMapperPreview {
//
//    CourseLecturePreviewResponse toDto(CourseLecture courseContent);
//    List<CourseLecturePreviewResponse> toDto(List<CourseLecture> courseContents);
//
//    @InheritInverseConfiguration
//    CourseLecture toEntity(CourseLecturePreviewResponse courseContentDto);
//    @InheritInverseConfiguration
//    List<CourseLecture> toEntity(List<CourseLecturePreviewResponse> courseContentDtos);
//}
