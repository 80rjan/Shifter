package com.shifterwebapp.shifter.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.service.CourseService;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.exception.ErrorResponse;
import com.shifterwebapp.shifter.upload.S3Service;
import com.shifterwebapp.shifter.upload.S3UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/courses")
@CrossOrigin
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Validate validate;
    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<?> getCourses(Authentication authentication) {
        List<Long> enrolledCourseIds = new ArrayList<>();
        if (authentication != null && authentication.isAuthenticated()) {
            Object detailsObj = authentication.getDetails();
            if (detailsObj instanceof CustomAuthDetails details) {
                Long userId = details.getUserId();
                enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
            }
        }

        List<CourseDtoPreview> courseDtos = courseService.getAllCourses(enrolledCourseIds);

        return ResponseEntity.ok(courseDtos);
    }

//    @GetMapping
//    public ResponseEntity<?> getCourses(
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false, name = "difficulty") List<Difficulty> difficulties,
//            @RequestParam(required = false, name = "price") List<String> prices,
//            @RequestParam(required = false, name = "duration") List<String> durations,
//            @RequestParam(required = false, name = "skill") List<String> skills,
//            @RequestParam(required = false, name = "topic") List<String> topics,
//            Authentication authentication
//            ) throws JsonProcessingException {
//        Specification<Course> spec = null;
//
//        if (search != null && !search.isEmpty()) {
//            spec = CourseSpecification.hasSearchLike(search);
//        }
//
//        if (difficulties != null && !difficulties.isEmpty()) {
//            spec = (spec == null) ? CourseSpecification.hasDifficulties(difficulties) : spec.and(CourseSpecification.hasDifficulties(difficulties));
//        }
//
//        if (prices != null && !prices.isEmpty()) {
//            spec = (spec == null) ? CourseSpecification.hasPricesBetween(prices) : spec.and(CourseSpecification.hasPricesBetween(prices));
//        }
//
//        if (durations != null && !durations.isEmpty()) {
//            spec = (spec == null) ? CourseSpecification.hasDurationBetween(durations) : spec.and(CourseSpecification.hasDurationBetween(durations));
//        }
//
//        if (skills != null && !skills.isEmpty()) {
//            spec = (spec == null) ? CourseSpecification.hasSkills(skills) : spec.and(CourseSpecification.hasSkills(skills));
//        }
//
//        if (topics != null && !topics.isEmpty()) {
//            spec = (spec == null) ? CourseSpecification.hasTopics(topics) : spec.and(CourseSpecification.hasTopics(topics));
//        }
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object detailsObj = authentication.getDetails();
//            if (detailsObj instanceof CustomAuthDetails details) {
//                Long userId = details.getUserId();
//                List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
//
//                if (enrolledCourseIds != null && !enrolledCourseIds.isEmpty()) {
//                    spec = (spec == null) ? CourseSpecification.idNotIn(enrolledCourseIds)
//                            : spec.and(CourseSpecification.idNotIn(enrolledCourseIds));
//                }
//            }
//        }
//
//        List<CourseDtoPreview> courseDtos = courseService.getAllCourses(spec);
//        return ResponseEntity.ok(courseDtos);
//    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedCourses(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            List<CourseDtoPreview> topRatedCourses = courseService.getTopRatedCourses();
//            List<CourseDto> mostPopularCourses = courseService.getMostPopularCourses();
            return ResponseEntity.ok(topRatedCourses);
        }

        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        List<CourseDtoPreview> recommendedCourses = courseService.getRecommendedCourses(userId);
        return ResponseEntity.ok(recommendedCourses);
    }

    @GetMapping("/enrolled")
    public ResponseEntity<?> getEnrolledCourses(Authentication authentication) {
        validate.validateUserIsAuthenticated(authentication);

        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        List<CourseDtoPreview> recommendedCourses = courseService.getEnrolledCourses(userId);
        return ResponseEntity.ok(recommendedCourses);
    }

    @GetMapping("/{courseId}/enrolled")
    public ResponseEntity<?> getEnrolledCourseById(
            @PathVariable("courseId") Long courseId,
            Authentication authentication) {
        Long userId = validate.extractUserId(authentication);

        CourseDtoFull courseDtoFull = courseService.getEnrolledCourseById(courseId, userId);
        return ResponseEntity.ok(courseDtoFull);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable("courseId") Long courseId) {
        CourseDtoDetail courseDto = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> getAllTopics() {
        return ResponseEntity.ok(courseService.getAllTopics());
    }

    @GetMapping("/skills")
    public ResponseEntity<List<String>> getAllSkills() {
        return ResponseEntity.ok(courseService.getAllSkills());
    }

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

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            // Cleanup — remove course from DB if file upload fails
            courseService.deleteCourseById(courseId);
            System.err.println("Error uploading files. Rolling back course. Reason: " + e.getMessage());

            return ResponseEntity.status(500).body("Course creation failed due to file upload error. Rolled back.");
        }
    }
}
