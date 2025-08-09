package com.shifterwebapp.shifter.upload;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.service.CourseService;
import com.shifterwebapp.shifter.courselecture.service.CourseLectureService;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
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
    private final CourseLectureService courseLectureService;
    private final Validate validate;

    @GetMapping("/presigned-url")
    public Map<String, String> getPresignedUrl(
            @RequestParam Long courseId,
            @RequestParam Long lectureId,
            @RequestParam String fileName,
            @RequestParam int expirySeconds,
            Authentication authentication
    ) {
        Long userId = validate.extractUserId(authentication);

        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId)) {
            throw new AccessDeniedException("You do not have access to this course content.");
        }

        System.out.println(fileName);
        System.out.println(courseId);
        String contentType = courseLectureService.getContentType(fileName, courseId, lectureId);
        if (contentType == null) {
            throw new ResourceNotFoundException("Content type not found for the specified file.");
        }

        String key = "private/courseContent/" + contentType.toLowerCase() + "/course" + courseId + "_" + fileName;
        System.out.println(key);

        if (!courseService.lectureFileExistsInCourse(courseId, fileName)) {
            throw new ResourceNotFoundException("File does not exist in the course.");
        }

        String url = s3Service.generatePresignedGetUrl(key, expirySeconds).toString();
        return Map.of("url", url);
    }
}
