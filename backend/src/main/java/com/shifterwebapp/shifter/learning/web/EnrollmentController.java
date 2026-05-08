//package com.shifterwebapp.shifter.learning.domain.controller;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.learning.web.response.EnrollmentResponse;
//import com.shifterwebapp.shifter.learning.application.EnrollmentService;
//import com.shifterwebapp.shifter.shared.exception.ErrorResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("${api.base.path}/enrollments")
//public class EnrollmentController {
//
//    private final EnrollmentService enrollmentService;
//    private final Validate validate;
//
//    @PostMapping("/create/{courseId}")
//    public ResponseEntity<?> enrollUserInCourse(@PathVariable Long courseId, Authentication authentication) {
//        Long userId = validate.extractUserId(authentication);
//
//        EnrollmentResponse enrollmentDto = enrollmentService.enrollUser(courseId, userId);
//        if (enrollmentDto == null) {
//            return ResponseEntity.badRequest().body(new ErrorResponse("Enrollment failed"));
//        }
//        return ResponseEntity.ok(enrollmentDto);
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<?> getEnrollmentsByUser(Authentication authentication) {
//        Long userId = validate.extractUserId(authentication);
//
//        List<EnrollmentResponse> enrollmentDtos = enrollmentService.getEnrollmentsByUser(userId);
//        return ResponseEntity.ok(enrollmentDtos);
//    }
//
//    @GetMapping("/{enrollmentId}")
//    public ResponseEntity<?> getEnrollmentById(@PathVariable Long enrollmentId) {
//        EnrollmentResponse enrollmentDto = enrollmentService.getEnrollmentById(enrollmentId);
//        return ResponseEntity.ok(enrollmentDto);
//    }
//}
