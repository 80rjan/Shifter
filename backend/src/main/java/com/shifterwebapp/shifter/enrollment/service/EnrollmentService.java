package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseRepository;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enrollment.EnrollmentMapper;
import com.shifterwebapp.shifter.enrollment.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.enums.PointsConstants;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserRepository;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService implements ImplEnrollmentService{

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final EnrollmentMapper enrollmentMapper;
    private final Validate validate;

    @Override
    public EnrollmentDto getEnrollmentById(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByUser(Long userId) {
        validate.validateUserExists(userId);
        List<Enrollment> enrollment = enrollmentRepository.findEnrollmentsByUser(userId);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Enrollment> enrollment = enrollmentRepository.findEnrollmentsByCourse(courseId);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public EnrollmentDto getEnrollmentByUserAndCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByUserAndCourse(userId, courseId);
        return enrollmentMapper.toDto(enrollment);
    }


    @Override
    public EnrollmentDto enrollUser(Long courseId, Long paymentId) {
        validate.validateCourseExists(courseId);
        validate.validatePaymentExists(paymentId);

        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new RuntimeException("Payment with ID " + paymentId + " is not completed successfully!");
        }

        Long userId = payment.getUser().getId();
        validate.validateUserExists(userId);
        boolean isAlreadyEnrolled = enrollmentRepository.findIsUserEnrolledInCourse(userId, courseId);
        if (isAlreadyEnrolled) {
            throw new RuntimeException("User with ID " + userId + " is already enrolled in course with ID " + courseId + "!");
        }

        Course course = courseRepository.findById(courseId).orElseThrow();

        Enrollment enrollment = Enrollment.builder()
                .enrollmentStatus(EnrollmentStatus.PENDING)
                .percentCompleted(0)
                .date(new Date())
                .payment(payment)
                .review(null)
                .course(course)
                .build();


        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public Boolean isUserEnrolledInCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.findIsUserEnrolledInCourse(userId, courseId);
    }

    @Override
    public EnrollmentDto updateEnrollmentStatusActive(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);
        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }

    // CALLING USER SERVICE HERE. IS THERE A BETTER WAY FOR THIS ???
    @Override
    public EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);

        Long userId = enrollment.getPayment().getUser().getId();
        List<Skills> skillsGained = enrollment.getCourse().getSkillsGained();
        userService.addPoints(userId, PointsConstants.BUY_COURSE);
        userService.addSkills(userId, skillsGained);
        userService.removeSkillGaps(userId, skillsGained);

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }
}
