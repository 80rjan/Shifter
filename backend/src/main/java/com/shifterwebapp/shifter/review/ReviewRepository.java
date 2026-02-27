package com.shifterwebapp.shifter.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select AVG(r.rating) from Review r where r.enrollment.courseVersion.course.id = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.courseVersion.course.id = :courseId")
    List<Review> findByCourseId(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.courseVersion.course.id in (:courseIds)")
    List<Review> findByCourseIdIn(@Param("courseIds") List<Long> courseIds);

    @Query("select r from Review r where r.enrollment.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Long userId);

    @Query("select r from Review r where r.enrollment.user.id = :userId and r.enrollment.courseVersion.course.id = :courseId")
    Optional<Review> findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);


    @Query("select r from Review r where r.enrollment.id = :enrollmentId")
    Review findByEnrollmentId(Long enrollmentId);

    @Query("select case when count(r) > 0 then true else false end" +
            " from Review r where r.enrollment.user.id = :userId and r.enrollment.courseVersion.course.id = :courseId")
    Boolean findHasBeenReviewedByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
