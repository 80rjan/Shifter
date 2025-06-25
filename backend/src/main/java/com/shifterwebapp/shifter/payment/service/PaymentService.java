package com.shifterwebapp.shifter.payment.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseRepository;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentRepository;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.PaymentDto;
import com.shifterwebapp.shifter.payment.PaymentMapper;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentMethod;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.account.Account;
import com.shifterwebapp.shifter.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements ImplPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AccountRepository accountRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final Validate validate;

    @Override
    public List<PaymentDto> getPaymentsByAccount(Long accountId) {
        validate.validateAccountExists(accountId);
        List<Payment> payments = paymentRepository.findPaymentByAccount_Id(accountId);
        return paymentMapper.toDto(payments);
    }

    @Override
    public List<PaymentDto> getPaymentsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Payment> payments = paymentRepository.findPaymentByCourse(courseId);
        return paymentMapper.toDto(payments);
    }

    @Override
    public Double getTotalRevenueByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalRevenueByCourse(courseId);
    }

    @Override
    public Double getTotalMonthlyRevenueByCourse(Long courseId, Integer month, Integer year) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalMonthlyRevenueByCourse(courseId, month, year);
    }

    @Override
    public Double getTotalYearlyRevenueByCourse(Long courseId, Integer year) {
        validate.validateCourseExists(courseId);
        return paymentRepository.findTotalYearlyRevenueByCourse(courseId, year);
    }

    @Override
    public Boolean hasAccountPaidForCourse(Long accountId, Long courseId) {
        validate.validateCourseExists(courseId);
        validate.validateAccountExists(accountId);
        return paymentRepository.findHasAccountPaidForCourse(accountId, courseId);
    }

    @Override
    public PaymentDto initiatePayment(Long accountId, Long courseId, PaymentMethod paymentMethod) {
        validate.validateAccountExists(accountId);
        validate.validateCourseExists(courseId);

        Account account = accountRepository.findById(accountId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        boolean isAlreadyEnrolled = enrollmentRepository.findIsAccountEnrolledInCourse(accountId, courseId);
        if (isAlreadyEnrolled) {
            throw new RuntimeException("Account with ID " + accountId + " is already enrolled in course with ID " + courseId + " and cannot initiate payment!");
        }

        // PAYMENT CODE (CASYS) HERE !!!!!!!!!
        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(paymentMethod)
                .date(new Date())
                .account(account)
                .enrollment(new Enrollment())
                .amount(course.getPrice())
                .build();

        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto completePayment(Long paymentId) {
        validate.validatePaymentExists(paymentId);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto failPayment(Long paymentId) {
        validate.validatePaymentExists(paymentId);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }
}
