package com.shifterwebapp.shifter.unittests;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseRepository;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enrollment.EnrollmentMapper;
import com.shifterwebapp.shifter.enrollment.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.account.Account;
import com.shifterwebapp.shifter.account.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestEnrollmentService {
    @Mock
    EnrollmentRepository enrollmentRepository;
    @Mock
    CourseRepository courseRepository;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    EnrollmentMapper enrollmentMapper;
    @Mock
    AccountService accountService;
    @Mock
    Validate validate;
    @InjectMocks
    EnrollmentService enrollmentService;

    Enrollment enrollment;

    @BeforeEach
    public void setUp() {
        enrollment = Enrollment.builder()
                .enrollmentStatus(EnrollmentStatus.PENDING)
                .percentCompleted(0)
                .date(new Date())
                .payment(new Payment())
                .review(null)
                .course(new Course())
                .build();
    }

    @Test
    public void test_getEnrollmentsByAccount() {
        Long accountId = 1L;

        List<Enrollment> enrollments = List.of(enrollment);

        List<EnrollmentDto> dtos = List.of(new EnrollmentDto());
        dtos.get(0).setPercentCompleted(1);
        dtos.get(0).setEnrollmentStatus(EnrollmentStatus.ACTIVE);


        Mockito.when(enrollmentRepository.findEnrollmentsByAccount(accountId)).thenReturn(enrollments);
        Mockito.doNothing().when(validate).validateAccountExists(accountId);
        Mockito.when(enrollmentMapper.toDto(enrollments)).thenReturn(dtos);

        List<EnrollmentDto> result = enrollmentService.getEnrollmentsByAccount(accountId);
        Assertions.assertEquals(dtos, result);
    }

    @Test
    public void test_updateEnrollmentStatusToCompleted() {
        Long enrollmentId = 1L;

        Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(mockAccount.getId()).thenReturn(2L);

        Payment mockPayment = Mockito.mock(Payment.class);
        Mockito.when(mockPayment.getAccount()).thenReturn(mockAccount);

        Course mockCourse = Mockito.mock(Course.class);
        Mockito.when(mockCourse.getSkillsGained()).thenReturn(List.of());

        enrollment.setPayment(mockPayment);
        enrollment.setCourse(mockCourse);

        EnrollmentDto dto = new EnrollmentDto();
        dto.setPercentCompleted(100);
        dto.setEnrollmentStatus(EnrollmentStatus.COMPLETED);

        Mockito.when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        Mockito.when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);
        Mockito.when(enrollmentMapper.toDto(enrollment)).thenReturn(dto);

        EnrollmentDto result = enrollmentService.updateEnrollmentStatusToCompleted(enrollmentId);

        Assertions.assertEquals(dto, result);
    }

    @Test
    public void test_updateEnrollmentStatusToActive() {
        Long enrollmentId = 1L;

        EnrollmentDto dto = new EnrollmentDto();
        dto.setPercentCompleted(1);
        dto.setEnrollmentStatus(EnrollmentStatus.ACTIVE);

        Mockito.when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        Mockito.when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);
        Mockito.when(enrollmentMapper.toDto(enrollment)).thenReturn(dto);

        EnrollmentDto result = enrollmentService.updateEnrollmentStatusToActive(enrollmentId);

        Assertions.assertEquals(dto, result);
    }

    @Test
    public void test_enrollAccount() {
        Long courseId = 1L;
        Long paymentId = 1L;
        Long accountId = 1L;
        EnrollmentDto dto = new EnrollmentDto();
        dto.setPercentCompleted(60);
        dto.setEnrollmentStatus(EnrollmentStatus.ACTIVE);

        Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(mockAccount.getId()).thenReturn(accountId);

        Payment mockPayment = Mockito.mock(Payment.class);
        Mockito.when(mockPayment.getAccount()).thenReturn(mockAccount);
        Mockito.when(mockPayment.getPaymentStatus()).thenReturn(PaymentStatus.COMPLETED);

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        Mockito.doNothing().when(validate).validateCourseExists(courseId);
        Mockito.doNothing().when(validate).validatePaymentExists(paymentId);
        Mockito.doNothing().when(validate).validateAccountExists(accountId);
        Mockito.when(enrollmentRepository.findIsAccountEnrolledInCourse(accountId, courseId)).thenReturn(false);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        Mockito.when(enrollmentRepository.save(Mockito.any(Enrollment.class))).thenAnswer(arguments -> arguments.getArgument(0));
        Mockito.when(enrollmentMapper.toDto(Mockito.any(Enrollment.class))).thenReturn(dto);

        EnrollmentDto result = enrollmentService.enrollAccount(courseId, paymentId);
        Assertions.assertEquals(dto, result);
    }
}
