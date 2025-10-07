package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseRepository;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enrollment.EnrollmentMapper;
import com.shifterwebapp.shifter.enrollment.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.*;
import com.shifterwebapp.shifter.exception.AlreadyEnrolledException;
import com.shifterwebapp.shifter.exception.PaymentNotCompleteException;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.service.PaymentService;
import com.shifterwebapp.shifter.user.service.UserService;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressRepository;
import com.shifterwebapp.shifter.usercourseprogress.service.UserCourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService implements ImplEnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserCourseProgressRepository userCourseProgressRepository;
    private final UserService userService;
    private final PaymentService paymentService;
    private final EnrollmentMapper enrollmentMapper;
    private final EmailService emailService;
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
    public List<Long> getCourseIdsByUserEnrollments(Long userId) {
        validate.validateUserExists(userId);
        return enrollmentRepository.getCourseIdsByUserEnrollments(userId);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Enrollment> enrollment = enrollmentRepository.findEnrollmentsByCourse(courseId);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public Enrollment getEnrollmentByUserAndCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.findEnrollmentByUserAndCourse(userId, courseId);
    }


    @Override
    public EnrollmentDto enrollUser(Long courseId, Long userId) {
        validate.validateCourseExists(courseId);
        validate.validateUserExists(userId);

        boolean isAlreadyEnrolled = enrollmentRepository.findIsUserEnrolledInCourse(userId, courseId);
        if (isAlreadyEnrolled) {
            throw new AlreadyEnrolledException("User with ID " + userId + " is already enrolled in course with ID " + courseId + "!");
        }

        Payment payment = paymentService.initiatePayment(userId, courseId, PaymentMethod.CASYS);

        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentNotCompleteException("Payment with ID " + payment.getId() + " is not completed successfully!");
        }

        Course course = courseRepository.findById(courseId).orElseThrow();

        Enrollment enrollment = Enrollment.builder()
                .enrollmentStatus(EnrollmentStatus.PENDING)
                .date(LocalDate.now())
                .payment(payment)
                .review(null)
                .course(course)
                .build();

        enrollmentRepository.save(enrollment);

        List<CourseLecture> courseLectures = course.getCourseContents().stream()
                .flatMap(content -> content.getCourseLectures().stream())
                .toList();

        List<UserCourseProgress> progressList = courseLectures.stream()
                .map(lecture -> UserCourseProgress.builder()
                        .courseLecture(lecture)
                        .enrollment(enrollment)
                        .completed(false)
                        .completedAt(null)
                        .build())
                .toList();

        userCourseProgressRepository.saveAll(progressList);

        String courseTitleFormatted = course.getTitleShort()
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");

        emailService.sendCoursePurchaseConfirmation(
                payment.getUser().getEmail(),
                course.getTitle(),
                course.getDescription(),
                "http://localhost:5173/learn/" + course.getId() + "/" + courseTitleFormatted,
                payment.getUser().getName()
        );

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public Boolean isUserEnrolledInCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.findIsUserEnrolledInCourse(userId, courseId);
    }

    @Override
    public EnrollmentDto updateEnrollmentStatusToActive(Enrollment enrollment) {
        validate.validateEnrollmentExists(enrollment.getId());

        if (enrollment.getEnrollmentStatus() == EnrollmentStatus.PENDING) {
            enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);
            enrollment.setActivatedAt(LocalDate.now());
            enrollmentRepository.save(enrollment);
        }

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        List<UserCourseProgress> userCourseProgresses = userCourseProgressRepository.findByEnrollmentId(enrollmentId);

        boolean allCompleted = userCourseProgresses.stream().allMatch(UserCourseProgress::isCompleted);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();

        if (allCompleted) {
            enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
            enrollment.setCompletedAt(LocalDate.now());
            enrollmentRepository.save(enrollment);

            Long userId = enrollment.getPayment().getUser().getId();
            List<String> skillsGained = enrollment.getCourse().getSkillsGained();
            userService.addPoints(userId, PointsConstants.BUY_COURSE);
            userService.addSkills(userId, skillsGained);
            userService.removeDesiredSkills(userId, skillsGained);
        }

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }


}
