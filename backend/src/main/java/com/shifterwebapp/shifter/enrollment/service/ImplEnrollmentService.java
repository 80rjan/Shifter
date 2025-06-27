package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;

import java.util.List;

public interface ImplEnrollmentService {
    EnrollmentDto getEnrollmentById(Long enrollmentId);
    List<EnrollmentDto> getEnrollmentsByUser(Long userId);
    List<EnrollmentDto> getEnrollmentsByCourse(Long courseId);
    EnrollmentDto getEnrollmentByUserAndCourse(Long userId, Long courseId);

    EnrollmentDto enrollUser(Long courseId, Long paymentId);

    Boolean isUserEnrolledInCourse(Long userId, Long courseId);

    EnrollmentDto updateEnrollmentStatusToActive(Long enrollmentId);
    EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId);
}
