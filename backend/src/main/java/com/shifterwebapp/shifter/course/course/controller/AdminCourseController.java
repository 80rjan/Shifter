package com.shifterwebapp.shifter.course.course.controller;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.coursetranslate.dto.CourseTranslateReq;
import com.shifterwebapp.shifter.course.course.service.AdminCourseService;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.external.upload.S3Service;
import com.shifterwebapp.shifter.external.upload.S3UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.admin.path}/courses")
@CrossOrigin
public class AdminCourseController {

    private final AdminCourseService adminCourseService;
    private final Validate validate;
    private final S3Service s3Service;


    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDtoFull> getFullCourse(
            @PathVariable("courseId") Long courseId,
            Authentication authentication,
            @RequestParam(defaultValue = "EN") Language language
    ) {
        validate.validateUserIsAdmin(authentication);

        CourseDtoFull dto = adminCourseService.getFullCourse(courseId, language);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createCourse(@RequestBody CourseDtoFull courseDtoFull, Authentication authentication) throws IOException {
        validate.validateUserIsAdmin(authentication);

        CourseVersion courseVersion = adminCourseService.createCourse(courseDtoFull);
        Long courseId = courseVersion.getCourse().getId();

        return ResponseEntity.ok(courseId);
    }


    @PostMapping("/translate")
    public ResponseEntity<Long> translateCourse(@RequestBody CourseTranslateReq courseTranslateReq, Authentication authentication) {
        validate.validateUserIsAdmin(authentication);

        Course course = adminCourseService.translateCourse(courseTranslateReq);
        Long courseId = course.getId();

        return ResponseEntity.ok(courseId);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadCourseFiles(
            @PathVariable("id") Long courseId,
            @RequestParam(value = "courseImage", required = false) MultipartFile courseImage,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("types") List<String> types,
            @RequestParam("meta") List<String> meta,
            @RequestParam(defaultValue = "EN") Language language,
            Authentication authentication
    )  {
        validate.validateUserIsAdmin(authentication);

        try {
            List<S3UploadResponse> s3UploadResponse =
                    s3Service.uploadCourseImageAndFiles(courseId, courseImage, files, types, meta);

            Course finalCourse = adminCourseService.updateCourseWithImagesAndFiles(courseId, s3UploadResponse, language);
            System.out.println("Final course: " + finalCourse);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            // Cleanup — remove course from DB if file upload fails
            adminCourseService.deleteCourseById(courseId);
            System.err.println("Error uploading files. Rolling back course. Reason: " + e.getMessage());

            return ResponseEntity.status(500).body("Course creation failed due to file upload error. Rolled back.");
        }
    }
}
