//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoDetail;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoLearn;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoPreview;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoPreviewEnrolled;
//
//import java.util.List;
//
//public interface ImplCourseService {
//
//    CourseDtoLearn getEnrolledCourseById(Long courseId, Long userId, LanguageCode language);
//
//    byte[] downloadCertificate(Long courseId, Long userId) throws Exception;
//
////    List<CourseDtoPreview> getAllCourses(Specification<Course> specification);
//    List<CourseDtoPreview> getAllCourses(List<Long> courseIds, LanguageCode language);
//    List<CourseDtoPreview> getRecommendedCourses(Long userId, LanguageCode language);
//    List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId, LanguageCode language);
//    List<CourseDtoPreview> getTopRatedCourses(LanguageCode language);
//    List<CourseDtoPreview> getMostPopularCourses(LanguageCode language);
//    CourseDtoDetail getCourseById(Long id, LanguageCode language);
//    Course getCourseEntityById(Long courseId);
//    Course getCourseEntityByLectureId(Long lectureId);
//
//    List<String> getAllTopics(LanguageCode language);
//    List<String> getAllSkills(LanguageCode language);
//
//}
