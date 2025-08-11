package com.shifterwebapp.shifter.usercourseprogress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseProgressRepository extends JpaRepository<UserCourseProgress, Long> {

    @Query("select ucp.enrollment.course.id from UserCourseProgress ucp where ucp.id = :progressId")
    Long getCourseId(@Param("progressId") Long progressId);

    @Query("select ucp from UserCourseProgress ucp where ucp.enrollment.id = :enrollmentId")
    List<UserCourseProgress> findByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
}
