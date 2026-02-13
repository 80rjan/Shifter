package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.course.repository.CourseRepository;
import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.course.courseversion.repository.CourseVersionRepository;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.dto.EnrollmentDto;
import com.shifterwebapp.shifter.enrollment.mapper.EnrollmentMapper;
import com.shifterwebapp.shifter.enrollment.repository.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.*;
import com.shifterwebapp.shifter.exception.AlreadyEnrolledException;
import com.shifterwebapp.shifter.exception.PaymentNotCompleteException;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.service.PaymentService;
import com.shifterwebapp.shifter.account.user.service.UserService;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private final CourseVersionRepository courseVersionRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    @Transactional(readOnly = true)
    public EnrollmentDto getEnrollmentById(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentDto> getEnrollmentsByUser(Long userId) {
        validate.validateUserExists(userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return enrollmentMapper.toDto(enrollments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsEntityByUser(Long userId) {
        validate.validateUserExists(userId);
        return enrollmentRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getCourseIdsByUserEnrollments(Long userId) {
        validate.validateUserExists(userId);
        return enrollmentRepository.findEnrolledCourseIdsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollmentMapper.toDto(enrollments);
    }

    @Override
    @Transactional(readOnly = true)
    public Enrollment getEnrollmentByUserAndCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }


    @Override
    @Transactional
    public EnrollmentDto enrollUser(Long courseId, Long userId) {
        validate.validateCourseExists(courseId);
        validate.validateUserExists(userId);

        boolean isAlreadyEnrolled = enrollmentRepository.existsByUserIdAndCourseVersion_Course_Id(userId, courseId);
        if (isAlreadyEnrolled) {
            throw new AlreadyEnrolledException("User with ID " + userId + " is already enrolled in course with ID " + courseId + "!");
        }

        Payment payment = paymentService.initiatePayment(userId, courseId, PaymentMethod.CASYS);

        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentNotCompleteException("Payment with ID " + payment.getId() + " is not completed successfully!");
        }

        Course course = courseRepository.findById(courseId).orElseThrow();
        CourseVersion courseVersion = courseVersionRepository.findByActiveTrueAndCourse_Id(courseId);

        Enrollment enrollment = Enrollment.builder()
                .enrollmentStatus(EnrollmentStatus.PENDING)
                .payment(payment)
                .review(null)
                .courseVersion(courseVersion)
                .build();

        enrollmentRepository.save(enrollment);

        List<CourseLecture> courseLectures = courseVersion.getCourseContents().stream()
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

        CourseTranslate courseTranslate = course.getTranslations()
                .stream()
                .filter(t -> t.getLanguage() == Language.EN)
                .toList()
                .get(0);
        String courseTitleFormatted = courseTranslate
                .getTitleShort()
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");

        emailService.sendCoursePurchaseConfirmation(
                enrollment.getUser().getEmail(),
                courseTranslate.getTitle(),
                courseTranslate.getDescription(),
                frontendUrl + "/learn/" + course.getId() + "/" + courseTitleFormatted,
                enrollment.getUser().getName()
        );

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isUserEnrolledInCourse(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.existsByUserIdAndCourseVersion_Course_Id(userId, courseId);
    }

    @Override
    @Transactional
    public EnrollmentDto updateEnrollmentStatusToActive(Enrollment enrollment) {
        validate.validateEnrollmentExists(enrollment.getId());

        if (enrollment.getEnrollmentStatus() == EnrollmentStatus.PENDING) {
            enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);
            enrollment.setActivationDate(LocalDate.now());
            enrollmentRepository.save(enrollment);
        }

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional
    public EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        List<UserCourseProgress> userCourseProgresses = userCourseProgressRepository.findByEnrollmentId(enrollmentId);

        boolean allCompleted = userCourseProgresses.stream().allMatch(UserCourseProgress::isCompleted);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();

        System.out.println(allCompleted);
        if (allCompleted) {
            System.out.println("UPDATING");
            enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
            enrollment.setCompletionDate(LocalDate.now());
            enrollmentRepository.save(enrollment);

            Long userId = enrollment.getUser().getId();
            List<Tag> courseTags = enrollment.getCourseVersion().getCourse().getTags();
            userService.addPoints(userId, PointsConstants.BUY_COURSE);
            userService.addTags(
                    userId,
                    Language.EN,
                    courseTags.stream()
                            .filter(a -> a.getType().equals(TagType.SKILL))
                            .map(Tag::getId)
                            .collect(Collectors.toList())
                    );
        }

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }


}
