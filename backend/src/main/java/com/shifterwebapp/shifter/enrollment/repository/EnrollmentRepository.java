package com.shifterwebapp.shifter.enrollment.repository;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.user.id = :userId")
    List<Enrollment> findByUserId(@Param("userId") Long userId);

    @Query("select e.courseVersion.course.id from Enrollment e where e.user.id = :userId")
    List<Long> findEnrolledCourseIdsByUserId(@Param("userId") Long userId);

    @Query("select e from Enrollment e where e.courseVersion.course.id = :courseId")
    List<Enrollment> findByCourseId(@Param("courseId") Long courseId);

    boolean existsByUserIdAndCourseVersion_Course_Id(Long userId, Long courseId);

    @Query("select e from Enrollment e where e.user.id = :userId and e.courseVersion.course.id = :courseId")
    Enrollment findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
