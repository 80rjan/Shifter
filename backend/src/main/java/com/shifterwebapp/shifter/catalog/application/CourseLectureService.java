//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.catalog.domain.CourseModule;
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslate;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoFull;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseLectureRepository;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseLectureTranslateRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CourseLectureService implements ImplCourseLectureService{
//
//    private final Validate validate;
//    private final CourseLectureRepository courseLectureRepository;
//    private final CourseLectureTranslateRepository courseLectureTranslateRepository;
//
//    // No transactional needed here because this is a builder method
//    public CourseLecture buildCourseLecture(CourseLectureDtoFull courseLectureDtoFull, CourseModule courseModule, LanguageCode language) {
//        CourseLecture lecture = CourseLecture.builder()
//                .durationMinutes(courseLectureDtoFull.getDurationMinutes())
//                .position(courseLectureDtoFull.getPosition())
//                .courseLectureContentType(courseLectureDtoFull.getCourseLectureContentType())
//                .courseModule(courseModule)
//                .build();
//
//        CourseLectureTranslate lectureTranslate = CourseLectureTranslate.builder()
//                .language(language)
//                .contentFileName(courseLectureDtoFull.getContentFileName())
//                .title(courseLectureDtoFull.getTitle())
//                .description(courseLectureDtoFull.getDescription())
//                .contentText(courseLectureDtoFull.getContentText())
//                .courseLecture(lecture)
//                .build();
//
//        lecture.setTranslations(List.of(lectureTranslate));
//
//        return lecture;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public CourseLectureTranslate getByCourseLectureIdAndLanguage(Long lectureId, LanguageCode language) {
//        return courseLectureTranslateRepository.findByIdAndLanguage(lectureId, language);
//    }
//}
