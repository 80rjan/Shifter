package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;

import java.util.List;

public interface ImplEnrollmentService {
    EnrollmentDto getEnrollmentById(Long enrollmentId);
    List<EnrollmentDto> getEnrollmentsByAccount(Long accountId);
    List<EnrollmentDto> getEnrollmentsByCourse(Long courseId);
    EnrollmentDto getEnrollmentByAccountAndCourse(Long accountId, Long courseId);

    EnrollmentDto enrollAccount(Long courseId, Long paymentId);

    Boolean isAccountEnrolledInCourse(Long accountId, Long courseId);

    EnrollmentDto updateEnrollmentStatusToActive(Long enrollmentId);
    EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId);
}
