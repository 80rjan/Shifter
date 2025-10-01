package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreviewEnrolled;
import com.shifterwebapp.shifter.upload.S3UploadResponse;

import java.util.List;

public interface ImplCourseService {
    Course createCourse(String courseJson) throws JsonProcessingException;
    void deleteCourseById(Long courseId);

    Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses);

    Boolean lectureFileExistsInCourse(Long courseId, String storagePath);

    CourseDtoFull getEnrolledCourseById(Long courseId, Long userId);

//    List<CourseDtoPreview> getAllCourses(Specification<Course> specification);
    List<CourseDtoPreview> getAllCourses(List<Long> courseIds);
    List<CourseDtoPreview> getRecommendedCourses(Long userId);
    List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId);
    List<CourseDtoPreview> getTopRatedCourses();
    List<CourseDtoPreview> getMostPopularCourses();
    CourseDtoDetail getCourseById(Long id);
    Course getCourseEntityById(Long courseId);

    List<String> getAllTopics();
    List<String> getAllSkills();
}
