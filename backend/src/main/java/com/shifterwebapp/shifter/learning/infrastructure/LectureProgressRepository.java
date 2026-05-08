package com.shifterwebapp.shifter.learning.infrastructure;
import com.shifterwebapp.shifter.learning.domain.LectureProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureProgressRepository extends JpaRepository<LectureProgress, Long> {

    @Query("select lp.enrollment.courseVersion.course.id from LectureProgress lp where lp.id = :progressId")
    Long getCourseId(@Param("progressId") Long progressId);

    @Query("select lp.enrollment.id from LectureProgress lp where lp.id = :progressId")
    Long getEnrollmentId(@Param("progressId") Long progressId);

    @Query("select lp from LectureProgress lp where lp.enrollment.id = :enrollmentId")
    List<LectureProgress> findByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

    @Query("select lp from LectureProgress lp where lp.enrollment.id = :enrollmentId and lp.completed = true")
    List<LectureProgress> findByEnrollmentIdAndCompletedTrue(@Param("enrollmentId") Long enrollmentId);

    @Query("select lp from LectureProgress lp where lp.enrollment.id in :enrollmentId and lp.completed = true")
    List<LectureProgress> findByEnrollmentIdsAndCompletedTrue(@Param("enrollmentId") List<Long> enrollmentIds);
}
