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
//    public CourseLecturePreviewResponse getCourseLectureDtoPreview(CourseLecture courseLecture, LanguageCode language) {
//        CourseLecturePreviewResponse courseLectureDtoPreview = courseLectureMapper.toDtoPreview(courseLecture, language);
//        return courseLectureDtoPreview;
//    }
//
//    public List<CourseLecturePreviewResponse> getCourseLectureDtoPreview(List<CourseLecture> courseLectures, LanguageCode language) {
//        List<CourseLecturePreviewResponse> courseLectureDtoPreviewList = new ArrayList<>();
//        for (CourseLecture courseLecture: courseLectures) {
//            CourseLecturePreviewResponse dto = getCourseLectureDtoPreview(courseLecture, language);
//
//            courseLectureDtoPreviewList.add(dto);
//        }
//
//        return courseLectureDtoPreviewList;
//    }
//
//    public CourseLectureLearningResponse getCourseLectureDtoLearn(CourseLecture courseLecture, LanguageCode language, Long userId) {
//        CourseLectureLearningResponse courseLectureDtoLearn = courseLectureMapper.toDtoLearn(courseLecture, language, userId);
//        return courseLectureDtoLearn;
//    }
//
//    public List<CourseLectureLearningResponse> getCourseLectureDtoLearn(List<CourseLecture> courseLectures, LanguageCode language, Long userId) {
//        List<CourseLectureLearningResponse> courseLectureDtoLearnList = new ArrayList<>();
//        for (CourseLecture courseLecture: courseLectures) {
//            CourseLectureLearningResponse dto = getCourseLectureDtoLearn(courseLecture, language, userId);
//
//            courseLectureDtoLearnList.add(dto);
//        }
//
//        return courseLectureDtoLearnList;
//    }
//}
