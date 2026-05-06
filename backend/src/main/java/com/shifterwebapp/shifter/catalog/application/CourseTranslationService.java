//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseTranslateRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CourseTranslateService implements ImplCourseTranslateService {
//
//    protected CourseTranslateRepository courseTranslateRepository;
//
//    @Override
//    public List<Language> getCourseTranslatedLanguages(Long courseId) {
//        return courseTranslateRepository.findByCourseId(courseId);
//    }
//}
