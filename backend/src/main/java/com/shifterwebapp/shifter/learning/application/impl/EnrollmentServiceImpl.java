package com.shifterwebapp.shifter.learning.application.impl;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.learning.web.response.EnrollmentDto;

import java.util.List;

public interface ImplEnrollmentService {
    EnrollmentDto getEnrollmentById(Long enrollmentId);
    List<EnrollmentDto> getEnrollmentsByUser(Long userId);
    List<Enrollment> getEnrollmentsEntityByUser(Long userId);
    List<Long> getCourseIdsByUserEnrollments(Long userId);
    List<EnrollmentDto> getEnrollmentsByCourse(Long courseId);
    Enrollment getEnrollmentByUserAndCourse(Long userId, Long courseId);

    EnrollmentDto enrollUser(Long courseId, Long userId);

    Boolean isUserEnrolledInCourse(Long userId, Long courseId);

    EnrollmentDto updateEnrollmentStatusToActive(Enrollment enrollment);
    EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId);

    Enrollment saveEnrollment(Enrollment enrollment);
}
