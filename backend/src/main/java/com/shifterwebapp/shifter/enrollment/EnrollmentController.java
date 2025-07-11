package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.course.CourseDtoPreview;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.exception.ErrorResponse;
import com.shifterwebapp.shifter.payment.service.PaymentService;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserRepository;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final Validate validate;

    @PostMapping("/create/{courseId}")
    public ResponseEntity<?> enrollUserInCourse(@PathVariable Long courseId, Authentication authentication) {
        validate.validateUserIsAuthenticated(authentication);

        CustomAuthDetails details = (CustomAuthDetails) authentication.getDetails();
        Long userId = details.getUserId();

        EnrollmentDto enrollmentDto = enrollmentService.enrollUser(courseId, userId);
        if (enrollmentDto == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Enrollment failed"));
        }
        return ResponseEntity.ok(enrollmentDto);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getEnrollmentsByUser(Authentication authentication) {
        validate.validateUserIsAuthenticated(authentication);

        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        List<EnrollmentDto> enrollmentDtos = enrollmentService.getEnrollmentsByUser(userId);
        return ResponseEntity.ok(enrollmentDtos);
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<?> getEnrollmentById(@PathVariable Long enrollmentId) {
        EnrollmentDto enrollmentDto = enrollmentService.getEnrollmentById(enrollmentId);
        return ResponseEntity.ok(enrollmentDto);
    }
}
