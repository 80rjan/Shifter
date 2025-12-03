package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.*;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.external.upload.S3UploadResponse;

import java.util.List;

public interface ImplCourseService {

    CourseDtoLearn getEnrolledCourseById(Long courseId, Long userId, Language language);

    byte[] downloadCertificate(Long courseId, Long userId) throws Exception;

//    List<CourseDtoPreview> getAllCourses(Specification<Course> specification);
    List<CourseDtoPreview> getAllCourses(List<Long> courseIds, Language language);
    List<CourseDtoPreview> getRecommendedCourses(Long userId, Language language);
    List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId, Language language);
    List<CourseDtoPreview> getTopRatedCourses(Language language);
    List<CourseDtoPreview> getMostPopularCourses(Language language);
    CourseDtoDetail getCourseById(Long id, Language language);
    Course getCourseEntityById(Long courseId);
    Course getCourseEntityByLectureId(Long lectureId);

    List<String> getAllTopics(Language language);
    List<String> getAllSkills(Language language);

}
