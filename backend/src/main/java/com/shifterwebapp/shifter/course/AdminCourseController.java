package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.service.CourseService;
import com.shifterwebapp.shifter.upload.S3Service;
import com.shifterwebapp.shifter.upload.S3UploadResponse;
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

    private final CourseService courseService;
    private final Validate validate;
    private final S3Service s3Service;

    @PostMapping("/create")
    public ResponseEntity<Long> createCourse(@RequestBody String courseJson, Authentication authentication) throws IOException {
        validate.validateUserIsAdmin(authentication);

        Course course = courseService.createCourse(courseJson);
        Long courseId = course.getId();

        return ResponseEntity.ok(courseId);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadCourseFiles(
            @PathVariable("id") Long courseId,
            @RequestParam("courseImage") MultipartFile courseImage,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("types") List<String> types,
            @RequestParam("meta") List<String> meta,
            Authentication authentication
    ) throws IOException {
        validate.validateUserIsAdmin(authentication);

        try {
            List<S3UploadResponse> s3UploadResponse =
                    s3Service.uploadCourseImageAndFiles(courseId, courseImage, files, types, meta);

            Course finalCourse = courseService.updateCourseWithImagesAndFiles(courseId, s3UploadResponse);
            System.out.println("Final course: " + finalCourse);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            // Cleanup — remove course from DB if file upload fails
            courseService.deleteCourseById(courseId);
            System.err.println("Error uploading files. Rolling back course. Reason: " + e.getMessage());

            return ResponseEntity.status(500).body("Course creation failed due to file upload error. Rolled back.");
        }
    }
}
