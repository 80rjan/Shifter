package com.shifterwebapp.shifter.external.upload;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.course.service.AdminCourseService;
import com.shifterwebapp.shifter.course.course.service.CourseService;
import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.courselecture.repository.CourseLectureTranslateRepository;
import com.shifterwebapp.shifter.courselecture.service.CourseLectureService;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.base.path}/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final AdminCourseService adminCourseService;
    private final CourseLectureService courseLectureService;
    private final Validate validate;
    private final CourseLectureTranslateRepository courseLectureTranslateRepository;

    @GetMapping("/presigned-url")
    public Map<String, String> getPresignedUrl(
            @RequestParam Long lectureId,
            @RequestParam Language language,
            @RequestParam int expirySeconds,
            Authentication authentication
    ) {
        Long userId = validate.extractUserId(authentication);

        Long courseId = courseService.getCourseEntityByLectureId(lectureId).getId();

        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId)) {
            throw new AccessDeniedException("You do not have access to this course content.");
        }

        CourseLectureTranslate lectureTranslate = courseLectureTranslateRepository.findByCourseLectureIdAndLanguage(lectureId, language);

        if (lectureTranslate == null) {
            throw new ResourceNotFoundException("Translation of lecture with it " + lectureId + " not found!");
        }

        String contentType = lectureTranslate.getCourseLecture().getContentType().toString();
        String fileName = lectureTranslate.getContentFileName();
        String key = "private/courseContent/" + contentType.toLowerCase() + "/course_" + courseId + "/" + fileName;

        if (!adminCourseService.lectureFileExistsInCourse(courseId, fileName)) {
            throw new ResourceNotFoundException("File does not exist in the course.");
        }

        String url = s3Service.generatePresignedGetUrl(key, expirySeconds).toString();
        return Map.of("url", url);
    }
}
