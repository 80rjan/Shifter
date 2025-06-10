package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enrollment.enums.EnrollmentStatus;

public interface ImplEnrollmentService {
    EnrollmentDto getEnrollmentsByUser(Long userId);
    EnrollmentDto getEnrollmentsByCourse(Long courseId);

    EnrollmentDto enrollUser(Long userId, Long courseId);

    Boolean isUserEnrolled(Long userId, Long courseId);

    EnrollmentDto updateEnrollmentsStatus(Long userId, Long courseId, EnrollmentStatus newStatus);

}
