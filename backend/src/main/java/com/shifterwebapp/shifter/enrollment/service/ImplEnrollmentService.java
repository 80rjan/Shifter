package com.shifterwebapp.shifter.enrollment.service;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentDto;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;

import java.util.List;

public interface ImplEnrollmentService {
    EnrollmentDto getEnrollmentById(Long enrollmentId);
    List<EnrollmentDto> getEnrollmentsByUser(Long userId);
    List<Long> getCourseIdsByUserEnrollments(Long userId);
    List<EnrollmentDto> getEnrollmentsByCourse(Long courseId);
    Enrollment getEnrollmentByUserAndCourse(Long userId, Long courseId);

    EnrollmentDto enrollUser(Long courseId, Long userId);

    Boolean isUserEnrolledInCourse(Long userId, Long courseId);

    EnrollmentDto updateEnrollmentStatusToActive(Long enrollmentId);
    EnrollmentDto updateEnrollmentStatusToCompleted(Long enrollmentId);
}
