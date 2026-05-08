//package com.shifterwebapp.shifter.consultation.infrastructure.upload;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.catalog.application.AdminCourseService;
//import com.shifterwebapp.shifter.catalog.application.CourseService;
//import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslation;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseLectureTranslateRepository;
//import com.shifterwebapp.shifter.catalog.application.CourseLectureService;
//import com.shifterwebapp.shifter.learning.application.EnrollmentService;
//import com.shifterwebapp.shifter.shared.exception.AccessDeniedException;
//import com.shifterwebapp.shifter.shared.exception.ResourceNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("${api.base.path}/s3")
//@RequiredArgsConstructor
//public class S3Controller {
//
//    private final S3Gateway s3Service;
//    private final EnrollmentService enrollmentService;
//    private final CourseService courseService;
//    private final AdminCourseService adminCourseService;
//    private final CourseLectureService courseLectureService;
//    private final Validate validate;
//    private final CourseLectureTranslateRepository courseLectureTranslateRepository;
//
//    @GetMapping("/presigned-url")
//    public Map<String, String> getPresignedUrl(
//            @RequestParam Long lectureId,
//            @RequestParam LanguageCode language,
//            @RequestParam int expirySeconds,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        Long courseId = courseService.getCourseEntityByLectureId(lectureId).getId();
//
//        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId)) {
//            throw new AccessDeniedException("You do not have access to this course content.");
//        }
//
//        CourseLectureTranslation lectureTranslate = courseLectureTranslateRepository.findByIdAndLanguage(lectureId, language);
//
//        if (lectureTranslate == null) {
//            throw new ResourceNotFoundException("Translation of lecture with it " + lectureId + " not found!");
//        }
//
//        String contentType = lectureTranslate.getCourseLecture().getCourseLectureContentType().toString();
//        String fileName = lectureTranslate.getContentFileName();
//        String key = "private/courseModule/" + contentType.toLowerCase() + "/course_" + courseId + "/" + fileName;
//
//        if (!adminCourseService.lectureFileExistsInCourse(courseId, fileName)) {
//            throw new ResourceNotFoundException("File does not exist in the course.");
//        }
//
//        String url = s3Service.generatePresignedGetUrl(key, expirySeconds).toString();
//        return Map.of("url", url);
//    }
//}
