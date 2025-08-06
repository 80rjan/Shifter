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
import com.shifterwebapp.shifter.enums.PaymentMethod;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.payment.PaymentRepository;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import com.shifterwebapp.shifter.payment.service.PaymentService;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.service.UserService;
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
    PaymentService paymentService;
    @Mock
    EnrollmentMapper enrollmentMapper;
    @Mock
    UserService userService;
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
    public void test_getEnrollmentsByUser() {
        Long userId = 1L;

        List<Enrollment> enrollments = List.of(enrollment);

        List<EnrollmentDto> dtos = List.of(new EnrollmentDto());
        dtos.get(0).setPercentCompleted(1);
        dtos.get(0).setEnrollmentStatus(EnrollmentStatus.ACTIVE);


        Mockito.when(enrollmentRepository.findEnrollmentsByUser(userId)).thenReturn(enrollments);
        Mockito.doNothing().when(validate).validateUserExists(userId);
        Mockito.when(enrollmentMapper.toDto(enrollments)).thenReturn(dtos);

        List<EnrollmentDto> result = enrollmentService.getEnrollmentsByUser(userId);
        Assertions.assertEquals(dtos, result);
    }

    @Test
    public void test_updateEnrollmentStatusToCompleted() {
        Long enrollmentId = 1L;

        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getId()).thenReturn(2L);

        Payment mockPayment = Mockito.mock(Payment.class);
        Mockito.when(mockPayment.getUser()).thenReturn(mockUser);

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
    public void test_enrollUser() {
        Long courseId = 1L;
        Long paymentId = 1L;
        Long userId = 1L;
        EnrollmentDto dto = new EnrollmentDto();
        dto.setPercentCompleted(60);
        dto.setEnrollmentStatus(EnrollmentStatus.ACTIVE);

        User mockUser = Mockito.mock(User.class);

        Payment mockPayment = Mockito.mock(Payment.class);
        Mockito.when(mockPayment.getPaymentStatus()).thenReturn(PaymentStatus.COMPLETED);

        Mockito.doNothing().when(validate).validateCourseExists(courseId);
        Mockito.doNothing().when(validate).validateUserExists(userId);
        Mockito.when(enrollmentRepository.findIsUserEnrolledInCourse(userId, courseId)).thenReturn(false);
        Mockito.when(paymentService.initiatePayment(userId, courseId, PaymentMethod.CASYS)).thenReturn(mockPayment);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        Mockito.when(enrollmentRepository.save(Mockito.any(Enrollment.class))).thenAnswer(arguments -> arguments.getArgument(0));
        Mockito.when(enrollmentMapper.toDto(Mockito.any(Enrollment.class))).thenReturn(dto);

        EnrollmentDto result = enrollmentService.enrollUser(courseId, paymentId);
        Assertions.assertEquals(dto, result);
    }
}
