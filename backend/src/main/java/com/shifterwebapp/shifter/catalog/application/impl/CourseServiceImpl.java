//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoDetail;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLearningResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CoursePreviewResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoPreviewEnrolled;
//
//import java.util.List;
//
//public interface ImplCourseService {
//
//    CourseLearningResponse getEnrolledCourseById(Long courseId, Long userId, LanguageCode language);
//
//    byte[] downloadCertificate(Long courseId, Long userId) throws Exception;
//
////    List<CoursePreviewResponse> getAllCourses(Specification<Course> specification);
//    List<CoursePreviewResponse> getAllCourses(List<Long> courseIds, LanguageCode language);
//    List<CoursePreviewResponse> getRecommendedCourses(Long userId, LanguageCode language);
//    List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId, LanguageCode language);
//    List<CoursePreviewResponse> getTopRatedCourses(LanguageCode language);
//    List<CoursePreviewResponse> getMostPopularCourses(LanguageCode language);
//    CourseDtoDetail getCourseById(Long id, LanguageCode language);
//    Course getCourseEntityById(Long courseId);
//    Course getCourseEntityByLectureId(Long lectureId);
//
//    List<String> getAllTopics(LanguageCode language);
//    List<String> getAllSkills(LanguageCode language);
//
//}
