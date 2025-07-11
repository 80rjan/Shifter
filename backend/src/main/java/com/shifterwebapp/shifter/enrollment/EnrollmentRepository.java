package com.shifterwebapp.shifter.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.payment.user.id = :userId")
    List<Enrollment> findEnrollmentsByUser(@Param("userId") Long userId);

    @Query("select e.course.id from Enrollment e where e.payment.user.id = :userId")
    List<Long> getCourseIdsByUserEnrollments(@Param("userId") Long userId);

    @Query("select e from Enrollment e where e.course.id = :courseId")
    List<Enrollment> findEnrollmentsByCourse(@Param("courseId") Long courseId);

    @Query("select case when count(e) > 0 then true else false end" +
            " from Enrollment e where e.payment.user.id = :userId and e.course.id = :courseId")
    Boolean findIsUserEnrolledInCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("select e from Enrollment e where e.payment.user.id = :userId and e.course.id = :courseId")
    Enrollment findEnrollmentByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
