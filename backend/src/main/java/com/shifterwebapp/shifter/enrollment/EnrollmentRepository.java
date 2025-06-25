package com.shifterwebapp.shifter.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.payment.account.id = :accountId")
    List<Enrollment> findEnrollmentsByAccount(@Param("accountId") Long accountId);

    @Query("select e from Enrollment e where e.course.id = :courseId")
    List<Enrollment> findEnrollmentsByCourse(@Param("courseId") Long courseId);

    @Query("select case when count(e) > 0 then true else false end" +
            " from Enrollment e where e.payment.account.id = :accountId and e.course.id = :courseId")
    Boolean findIsAccountEnrolledInCourse(@Param("accountId") Long accountId, @Param("courseId") Long courseId);

    @Query("select e from Enrollment e where e.payment.account.id = :accountId and e.course.id = :courseId")
    Enrollment findEnrollmentByAccountAndCourse(@Param("accountId") Long accountId, @Param("courseId") Long courseId);
}
