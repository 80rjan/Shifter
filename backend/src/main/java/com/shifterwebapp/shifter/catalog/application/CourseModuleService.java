//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.catalog.web.response.CourseModulePreviewResponse;
//import com.shifterwebapp.shifter.catalog.domain.CourseModule;
//import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
//import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslation;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleFullResponse;
//import com.shifterwebapp.shifter.catalog.infrastructure.mapper.CourseModuleMapper;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseModuleRepository;
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CourseModuleService implements ImplCourseModuleService {
//
//    private final CourseModuleRepository courseModuleRepository;
//    private final CourseLectureService courseLectureService;
//    private final CourseModuleMapper courseModuleMapper;
//
//    // No transactional annotation here because this is a builder method
//    @Override
//    public CourseModule buildCourseContent(CourseModuleFullResponse courseModuleDtoFull, CourseVersion courseVersion, LanguageCode language) {
//        CourseModule content = CourseModule.builder()
//                .position(courseModuleDtoFull.getPosition())
//                .courseVersion(courseVersion)
//                .build();
//
//        CourseModuleTranslation contentTranslate = CourseModuleTranslation.builder()
//                .title(courseModuleDtoFull.getTitle())
//                .language(language)
//                .courseModule(content)
//                .build();
//
//        content.setTranslations(List.of(contentTranslate));
//
//        List<CourseLecture> lectureList = courseModuleDtoFull.getCourseLectures().stream()
//                .map(lecture -> courseLectureService.buildCourseLecture(lecture, content, language))
//                .toList();
//
//        content.setCourseLectures(lectureList);
//        return content;
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<CourseModulePreviewResponse> getCourseContentByCourseId(
//            Long courseId,
//            LanguageCode language
//            ) {
//        List<CourseModule> courseModules = courseModuleRepository.findByCourseId(courseId);
//        return courseModuleMapper.toDtoPreview(courseModules, language);
//    }
//}
