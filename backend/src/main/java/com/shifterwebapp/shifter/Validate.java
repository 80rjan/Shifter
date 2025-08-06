package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.course.CourseRepository;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import com.shifterwebapp.shifter.exception.UnauthorizedException;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validate {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;

    public void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found!");
        }
    }

    public void validateUserExists(String email) {
        if (!userRepository.existsUserByEmail(email)) {
            throw new ResourceNotFoundException("User with email " + email + " not found!");
        }
    }

    public void validateUserIsAuthenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new UnauthorizedException("User is not authenticated");
        }
    }

    public void validateUserIsAdmin(Authentication authentication) {
        validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (detailsObj instanceof CustomAuthDetails details) {
            Long userId = details.getUserId();
            boolean isAdmin = userRepository.isAdmin(userId);
            if (!isAdmin) {
                throw new UnauthorizedException("User is not an admin");
            }
        } else {
            throw new UnauthorizedException("User is not an admin");
        }
    }


    public void validateCourseExists(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course with ID " + courseId + " not found!");
        }
    }

    public void validatePaymentExists(Long paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new ResourceNotFoundException("Payment with ID " + paymentId + " not found!");
        }
    }

    public void validateEnrollmentExists(Long enrollmentId) {
        if (!paymentRepository.existsById(enrollmentId)) {
            throw new ResourceNotFoundException("Enrollment with ID " + enrollmentId + " not found!");
        }
    }
}
