//package com.shifterwebapp.shifter.course.services;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoFull;
//import com.shifterwebapp.shifter.catalog.web.response.CourseTranslateReq;
//import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
//import com.shifterwebapp.shifter.infrastructure.storage.S3UploadResponse;
//
//import java.util.List;
//
//public interface ImplAdminCourseService {
//    CourseDtoFull getFullCourse(Long courseId, LanguageCode language);
//
//    CourseVersion createCourse(CourseDtoFull courseDtoFull, Long exertId) throws JsonProcessingException;
//    Course translateCourse(CourseTranslateReq courseTranslateReq);
//    void deleteCourseById(Long courseId);
//
//    Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses, LanguageCode language);
//    Boolean lectureFileExistsInCourse(Long courseId, String storagePath);
//
//}
