package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.attribute.repository.AttributeRepository;
import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.course.course.repository.CourseRepository;
import com.shifterwebapp.shifter.courselecture.repository.CourseLectureRepository;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.BadRequestException;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import com.shifterwebapp.shifter.exception.TranslationAlreadyExistsException;
import com.shifterwebapp.shifter.exception.UnauthorizedException;
import com.shifterwebapp.shifter.payment.repository.PaymentRepository;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class Validate {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseProgressRepository userCourseProgressRepository;
    private final PaymentRepository paymentRepository;
    private final AttributeRepository attributeRepository;
    private final CourseLectureRepository courseLectureRepository;

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

    public Long extractUserId(Authentication authentication) {
        validateUserIsAuthenticated(authentication);

        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            throw new BadRequestException("Invalid authentication details");
        }
        return details.getUserId();
    }


    // TODO: make this method work properly
    public void validateUserIsAdmin(Authentication authentication) {
        validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (detailsObj instanceof CustomAuthDetails details) {
            Long userId = details.getUserId();
            boolean isAdmin = false;
            if (!isAdmin) {
                throw new UnauthorizedException("User is not an admin");
            }
        } else {
            throw new UnauthorizedException("User is not an admin");
        }
    }

    public void validateUserProfileNotComplete(User user) {
        if (user.isProfileComplete())
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User already completed profile and does not need verification token.");
    }


    public void validateCourseExists(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course with ID " + courseId + " not found!");
        }
    }

    public void validateLectureExists(Long lectureId) {
        if (!courseLectureRepository.existsById(lectureId)) {
            throw new ResourceNotFoundException("Lecture with ID " + lectureId + " not found!");
        }
    }

    public void validateUserCourseProgressExists(Long progressId) {
        if (!userCourseProgressRepository.existsById(progressId)) {
            throw new ResourceNotFoundException("UserCourseProgress with ID " + progressId + " not found!");
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

    public void validateAttributeExists(Long attributeId) {
        if (!attributeRepository.existsById(attributeId)) {
            throw new ResourceNotFoundException("Attribute with ID " + attributeId + " not found!");
        }
    }

    public void validateCourseTranslation(Long courseId, Language language) {
        if (courseRepository.courseHasBeenTranslated(courseId, language)) {
            throw new TranslationAlreadyExistsException("Course with id " + courseId + " already has a translation for language " + language);
        }
    }
}
