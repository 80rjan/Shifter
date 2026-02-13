package com.shifterwebapp.shifter.payment.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.course.repository.CourseRepository;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.repository.EnrollmentRepository;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.dto.PaymentDto;
import com.shifterwebapp.shifter.payment.mapper.PaymentMapper;
import com.shifterwebapp.shifter.payment.repository.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentMethod;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements ImplPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final Validate validate;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByUser(Long userId) {
        validate.validateUserExists(userId);
        List<Payment> payments = paymentRepository.findPaymentByUser(userId);
        return paymentMapper.toDto(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Payment> payments = paymentRepository.findPaymentByCourse(courseId);
        return paymentMapper.toDto(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalRevenueByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalRevenueByCourse(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalMonthlyRevenueByCourse(courseId, month, year);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalYearlyRevenueByCourse(Long courseId, Integer year) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalYearlyRevenueByCourse(courseId, year);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasUserPaidForCourse(Long userId, Long courseId) {
        validate.validateCourseExists(courseId);
        validate.validateUserExists(userId);
        return paymentRepository.findHasUserPaidForCourse(userId, courseId);
    }

    @Override
    @Transactional
    public Payment initiatePayment(Long userId, Long courseId, PaymentMethod paymentMethod) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);

        // PAYMENT CODE (CASYS) HERE !!!!!!!!!

        Payment payment = Payment.builder()
                .amount(course.getPrice())
                .paymentDate(LocalDate.now())
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.COMPLETED)
                .enrollment(enrollment)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public PaymentDto completePayment(Long paymentId) {
        validate.validatePaymentExists(paymentId);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentDto failPayment(Long paymentId) {
        validate.validatePaymentExists(paymentId);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }
}
