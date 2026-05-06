//package com.shifterwebapp.shifter;
//
//import com.shifterwebapp.shifter.shared.domain.LanguageCode;
//import com.shifterwebapp.shifter.auth.web.request.CustomAuthDetails;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseRepository;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseLectureRepository;
//import com.shifterwebapp.shifter.shared.exception.BadRequestException;
//import com.shifterwebapp.shifter.shared.exception.ResourceNotFoundException;
//import com.shifterwebapp.shifter.shared.exception.TranslationAlreadyExistsException;
//import com.shifterwebapp.shifter.shared.exception.UnauthorizedException;
//import com.shifterwebapp.shifter.commerce.infrastructure.PaymentRepository;
//import com.shifterwebapp.shifter.identity.domain.User;
//import com.shifterwebapp.shifter.identity.infrastructure.UserRepository;
//import com.shifterwebapp.shifter.catalog.infrastructure.LectureProgressRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//@Component
//@RequiredArgsConstructor
//public class Validate {
//
//    private final UserRepository userRepository;
//    private final CourseRepository courseRepository;
//    private final LectureProgressRepository lectureProgressRepository;
//    private final PaymentRepository paymentRepository;
//    private final TagRepository tagRepository;
//    private final CourseLectureRepository courseLectureRepository;
//
//    public void validateUserExists(Long userId) {
//        if (!userRepository.existsById(userId)) {
//            throw new ResourceNotFoundException("User with ID " + userId + " not found!");
//        }
//    }
//
//    public void validateUserExists(String email) {
//        if (!userRepository.existsUserByEmail(email)) {
//            throw new ResourceNotFoundException("User with email " + email + " not found!");
//        }
//    }
//
//    public void validateUserIsAuthenticated(Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
//            throw new UnauthorizedException("User is not authenticated");
//        }
//    }
//
//    public Long extractUserId(Authentication authentication) {
//        validateUserIsAuthenticated(authentication);
//
//        Object detailsObj = authentication.getDetails();
//        if (!(detailsObj instanceof CustomAuthDetails details)) {
//            throw new BadRequestException("Invalid authentication details");
//        }
//        return details.getUserId();
//    }
//
//
//    public void validateExpert(Authentication authentication) {
//        validateUserIsAuthenticated(authentication);
//
//        Object detailsObj = authentication.getDetails();
//        if (detailsObj instanceof CustomAuthDetails details) {
//            String role = details.getRole();
//
//            if (!role.equals("EXPERT")) {
//                throw new UnauthorizedException("User is not an expert");
//            }
//        } else {
//            throw new UnauthorizedException("User is not an expert");
//        }
//    }
//
//    public Long extractExpertId(Authentication authentication) {
//        validateExpert(authentication);
//
//        Object detailsObj = authentication.getDetails();
//        if (!(detailsObj instanceof CustomAuthDetails details)) {
//            throw new BadRequestException("Invalid authentication details");
//        }
//        return details.getUserId();
//    }
//
//    public void validateUserProfileNotComplete(User user) {
//        if (user.isProfileComplete())
//            throw new ResponseStatusException(HttpStatus.CONFLICT,
//                    "User already completed profile and does not need verification token.");
//    }
//
//
//    public void validateCourseExists(Long courseId) {
//        if (!courseRepository.existsById(courseId)) {
//            throw new ResourceNotFoundException("Course with ID " + courseId + " not found!");
//        }
//    }
//
//    public void validateLectureExists(Long lectureId) {
//        if (!courseLectureRepository.existsById(lectureId)) {
//            throw new ResourceNotFoundException("Lecture with ID " + lectureId + " not found!");
//        }
//    }
//
//    public void validateUserCourseProgressExists(Long progressId) {
//        if (!lectureProgressRepository.existsById(progressId)) {
//            throw new ResourceNotFoundException("LectureProgress with ID " + progressId + " not found!");
//        }
//    }
//
//    public void validatePaymentExists(Long paymentId) {
//        if (!paymentRepository.existsById(paymentId)) {
//            throw new ResourceNotFoundException("Payment with ID " + paymentId + " not found!");
//        }
//    }
//
//    public void validateEnrollmentExists(Long enrollmentId) {
//        if (!paymentRepository.existsById(enrollmentId)) {
//            throw new ResourceNotFoundException("Enrollment with ID " + enrollmentId + " not found!");
//        }
//    }
//
//    public void validateTagExists(Long tagId) {
//        if (!tagRepository.existsById(tagId)) {
//            throw new ResourceNotFoundException("Tag with ID " + tagId + " not found!");
//        }
//    }
//
//    public void validateCourseTranslation(Long courseId, LanguageCode language) {
//        if (courseRepository.courseHasBeenTranslated(courseId, language)) {
//            throw new TranslationAlreadyExistsException("Course with id " + courseId + " already has a translation for language " + language);
//        }
//    }
//}
