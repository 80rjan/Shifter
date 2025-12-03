package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoTranslate;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.external.upload.S3UploadResponse;

import java.util.List;

public interface ImplAdminCourseService {
    CourseDtoFull getFullCourse(Long courseId);

    Course createCourse(CourseDtoFull courseDtoFull) throws JsonProcessingException;
    Course translateCourse(CourseDtoTranslate courseDtoTranslate);
    void deleteCourseById(Long courseId);

    Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses, Language language);
    Boolean lectureFileExistsInCourse(Long courseId, String storagePath);

}
