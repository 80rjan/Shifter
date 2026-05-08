package com.shifterwebapp.shifter.learning.application.impl;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.learning.web.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentServiceImpl {
    EnrollmentResponse getEnrollmentById(Long enrollmentId);
    List<EnrollmentResponse> getEnrollmentsByUser(Long userId);
    List<Enrollment> getEnrollmentsEntityByUser(Long userId);
    List<Long> getCourseIdsByUserEnrollments(Long userId);
    List<EnrollmentResponse> getEnrollmentsByCourse(Long courseId);
    Enrollment getEnrollmentByUserAndCourse(Long userId, Long courseId);

    EnrollmentResponse enrollUser(Long courseId, Long userId);

    Boolean isUserEnrolledInCourse(Long userId, Long courseId);

    EnrollmentResponse updateEnrollmentStatusToActive(Enrollment enrollment);
    EnrollmentResponse updateEnrollmentStatusToCompleted(Long enrollmentId);

    Enrollment saveEnrollment(Enrollment enrollment);
}
