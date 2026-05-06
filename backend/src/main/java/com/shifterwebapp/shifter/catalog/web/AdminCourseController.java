//package com.shifterwebapp.shifter.course.controllers;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoFull;
//import com.shifterwebapp.shifter.catalog.web.response.CourseTranslateReq;
//import com.shifterwebapp.shifter.catalog.application.AdminCourseService;
//import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
//import com.shifterwebapp.shifter.infrastructure.storage.S3Service;
//import com.shifterwebapp.shifter.infrastructure.storage.S3UploadResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${api.admin.path}/courses")
//@CrossOrigin
//public class AdminCourseController {
//
//    private final AdminCourseService adminCourseService;
//    private final Validate validate;
//    private final S3Service s3Service;
//
//
//    @GetMapping("/{courseId}")
//    public ResponseEntity<CourseDtoFull> getFullCourse(
//            @PathVariable("courseId") Long courseId,
//            Authentication authentication,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//    ) {
//        validate.validateExpert(authentication);
//
//        CourseDtoFull dto = adminCourseService.getFullCourse(courseId, language);
//        return ResponseEntity.ok(dto);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Long> createCourse(@RequestBody CourseDtoFull courseDtoFull, Authentication authentication) throws IOException {
//        Long expertId = validate.extractExpertId(authentication);
//
//        CourseVersion courseVersion = adminCourseService.createCourse(courseDtoFull, expertId);
//        Long courseId = courseVersion.getCourse().getId();
//
//        return ResponseEntity.ok(courseId);
//    }
//
//
//    @PostMapping("/translate")
//    public ResponseEntity<Long> translateCourse(@RequestBody CourseTranslateReq courseTranslateReq, Authentication authentication) {
//        validate.validateExpert(authentication);
//
//        Course course = adminCourseService.translateCourse(courseTranslateReq);
//        Long courseId = course.getId();
//
//        return ResponseEntity.ok(courseId);
//    }
//
//    @PostMapping("/{id}/upload")
//    public ResponseEntity<?> uploadCourseFiles(
//            @PathVariable("id") Long courseId,
//            @RequestPart(value = "courseImage", required = false) MultipartFile courseImage,
//            @RequestPart(value = "files", required = false) List<MultipartFile> files,
//            @RequestPart(value = "types", required = false) List<String> types,
//            @RequestPart(value = "meta", required = false) List<String> meta,
//            @RequestParam(defaultValue = "EN") LanguageCode language,
//            Authentication authentication
//    )  {
//        validate.validateExpert(authentication);
//
//        try {
//            if (files == null) {
//                files = List.of(); // Empty list
//            }
//            if (types == null) {
//                types = List.of();
//            }
//            if (meta == null) {
//                meta = List.of();
//            }
//
//            List<S3UploadResponse> s3UploadResponse =
//                    s3Service.uploadCourseImageAndFiles(courseId, courseImage, files, types, meta);
//
//            Course finalCourse = adminCourseService.updateCourseWithImagesAndFiles(courseId, s3UploadResponse, language);
//
//            return ResponseEntity.ok(null);
//        } catch (Exception e) {
//            // Cleanup — remove course from DB if file upload fails
//            adminCourseService.deleteCourseById(courseId);
//            System.err.println("Error uploading files. Rolling back course. Reason: " + e.getMessage());
//
//            return ResponseEntity.status(500).body("Course creation failed due to file upload error. Rolled back.");
//        }
//    }
//}
