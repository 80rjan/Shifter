//package com.shifterwebapp.shifter.course.dtos;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.infrastructure.mapper.CourseLectureMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class CourseLectureDtoBuilder {
//
//    private final CourseLectureMapper courseLectureMapper;
//
//
//    public CourseLectureDtoPreview getCourseLectureDtoPreview(CourseLecture courseLecture, LanguageCode language) {
//        CourseLectureDtoPreview courseLectureDtoPreview = courseLectureMapper.toDtoPreview(courseLecture, language);
//        return courseLectureDtoPreview;
//    }
//
//    public List<CourseLectureDtoPreview> getCourseLectureDtoPreview(List<CourseLecture> courseLectures, LanguageCode language) {
//        List<CourseLectureDtoPreview> courseLectureDtoPreviewList = new ArrayList<>();
//        for (CourseLecture courseLecture: courseLectures) {
//            CourseLectureDtoPreview dto = getCourseLectureDtoPreview(courseLecture, language);
//
//            courseLectureDtoPreviewList.add(dto);
//        }
//
//        return courseLectureDtoPreviewList;
//    }
//
//    public CourseLectureDtoLearn getCourseLectureDtoLearn(CourseLecture courseLecture, LanguageCode language, Long userId) {
//        CourseLectureDtoLearn courseLectureDtoLearn = courseLectureMapper.toDtoLearn(courseLecture, language, userId);
//        return courseLectureDtoLearn;
//    }
//
//    public List<CourseLectureDtoLearn> getCourseLectureDtoLearn(List<CourseLecture> courseLectures, LanguageCode language, Long userId) {
//        List<CourseLectureDtoLearn> courseLectureDtoLearnList = new ArrayList<>();
//        for (CourseLecture courseLecture: courseLectures) {
//            CourseLectureDtoLearn dto = getCourseLectureDtoLearn(courseLecture, language, userId);
//
//            courseLectureDtoLearnList.add(dto);
//        }
//
//        return courseLectureDtoLearnList;
//    }
//}
