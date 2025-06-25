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
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.account.service.AccountService;
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
    private final AccountService accountService;
    private final EnrollmentMapper enrollmentMapper;
    private final Validate validate;

    @Override
    public EnrollmentDto getEnrollmentById(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByAccount(Long accountId) {
        validate.validateAccountExists(accountId);
        List<Enrollment> enrollment = enrollmentRepository.findEnrollmentsByAccount(accountId);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Enrollment> enrollment = enrollmentRepository.findEnrollmentsByCourse(courseId);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public EnrollmentDto getEnrollmentByAccountAndCourse(Long accountId, Long courseId) {
        validate.validateAccountExists(accountId);
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByAccountAndCourse(accountId, courseId);
        return enrollmentMapper.toDto(enrollment);
    }


    @Override
    public EnrollmentDto enrollAccount(Long courseId, Long paymentId) {
        validate.validateCourseExists(courseId);
        validate.validatePaymentExists(paymentId);

        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new RuntimeException("Payment with ID " + paymentId + " is not completed successfully!");
        }

        Long accountId = payment.getAccount().getId();
        validate.validateAccountExists(accountId);
        boolean isAlreadyEnrolled = enrollmentRepository.findIsAccountEnrolledInCourse(accountId, courseId);
        if (isAlreadyEnrolled) {
            throw new RuntimeException("account with ID " + accountId + " is already enrolled in course with ID " + courseId + "!");
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

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public Boolean isAccountEnrolledInCourse(Long accountId, Long courseId) {
        validate.validateAccountExists(accountId);
        validate.validateCourseExists(courseId);

        return enrollmentRepository.findIsAccountEnrolledInCourse(accountId, courseId);
    }

    @Override
    public EnrollmentDto updateEnrollmentStatusToActive(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);
        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }

    // CALLING ACCOUNT SERVICE HERE. IS THERE A BETTER WAY FOR THIS ???
    @Override
    public EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);

        Long accountId = enrollment.getPayment().getAccount().getId();
        List<Skills> skillsGained = enrollment.getCourse().getSkillsGained();
        accountService.addPoints(accountId, PointsConstants.BUY_COURSE);
        accountService.addSkills(accountId, skillsGained);
        accountService.removeSkillGaps(accountId, skillsGained);

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }
}
