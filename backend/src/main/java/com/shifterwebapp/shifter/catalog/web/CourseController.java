//package com.shifterwebapp.shifter.course.controllers;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.shared.domain.LanguageCode;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoDetail;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoLearn;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoPreview;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoPreviewEnrolled;
//import com.shifterwebapp.shifter.catalog.application.CourseService;
//import com.shifterwebapp.shifter.learning.application.EnrollmentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${api.base.path}/courses")
//@CrossOrigin
//public class CourseController {
//
//    private final CourseService courseService;
//    private final EnrollmentService enrollmentService;
//    private final Validate validate;
//
//    @GetMapping
//    public ResponseEntity<List<CourseDtoPreview>> getCourses(
//            Authentication authentication,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//            ) {
//        List<Long> enrolledCourseIds = new ArrayList<>();
//        if (authentication != null && authentication.isAuthenticated()) {
//            Long userId = validate.extractUserId(authentication);
//            enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
//        }
//
//
//        List<CourseDtoPreview> courseDtos = courseService.getAllCourses(enrolledCourseIds, language);
//
//        return ResponseEntity.ok(courseDtos);
//    }
//
//    @GetMapping("/recommended")
//    public ResponseEntity<List<CourseDtoPreview>> getRecommendedCourses(
//            Authentication authentication,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//            ) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            List<CourseDtoPreview> topRatedCourses = courseService.getTopRatedCourses(language);
////            List<CourseDto> mostPopularCourses = courseService.getMostPopularCourses(language);
//            return ResponseEntity.ok(topRatedCourses);
//        }
//
//        Long userId = validate.extractUserId(authentication);
//
//        List<CourseDtoPreview> recommendedCourses = courseService.getRecommendedCourses(userId, language);
//        return ResponseEntity.ok(recommendedCourses);
//    }
//
//    @GetMapping("/enrolled")
//    public ResponseEntity<List<CourseDtoPreviewEnrolled>> getEnrolledCourses(
//            Authentication authentication,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//            ) {
//        Long userId = validate.extractUserId(authentication);
//
//        List<CourseDtoPreviewEnrolled> recommendedCourses = courseService.getEnrolledCourses(userId, language);
//        return ResponseEntity.ok(recommendedCourses);
//    }
//
//    @GetMapping("/enrolled/{courseId}")
//    public ResponseEntity<CourseDtoLearn> getEnrolledCourseById(
//            @PathVariable("courseId") Long courseId,
//            Authentication authentication,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        CourseDtoLearn courseDtoLearn = courseService.getEnrolledCourseById(courseId, userId, language);
//        return ResponseEntity.ok(courseDtoLearn);
//    }
//
//    @GetMapping("/{courseId}")
//    public ResponseEntity<CourseDtoDetail> getCourseById(
//            @PathVariable("courseId") Long courseId,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//            ) {
//        CourseDtoDetail courseDto = courseService.getCourseById(courseId, language);
//        return ResponseEntity.ok(courseDto);
//    }
//
//    @GetMapping("/{courseId}/certificate")
//    public ResponseEntity<byte[]> getCourseCertificate(@PathVariable("courseId") Long courseId, Authentication authentication) throws Exception {
//        Long userId = validate.extractUserId(authentication);
//
//        byte[] personalizedPdf = courseService.downloadCertificate(courseId, userId);
//
//        String fileName = courseService.getCourseById(courseId, Language.EN).getTitleShort().replaceAll("\\s+", "_") + "_Certificate.pdf";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .contentType(MediaType.APPLICATION_PDF)
//                .contentLength(personalizedPdf.length)
//                .body(personalizedPdf);
//    }
//
//    @GetMapping("/topics")
//    public ResponseEntity<List<String>> getAllTopics(@RequestParam(defaultValue = "EN") LanguageCode language) {
//        return ResponseEntity.ok(courseService.getAllTopics(language));
//    }
//
//    @GetMapping("/skills")
//    public ResponseEntity<List<String>> getAllSkills(@RequestParam(defaultValue = "EN") LanguageCode language) {
//        return ResponseEntity.ok(courseService.getAllSkills(language));
//    }
//}
