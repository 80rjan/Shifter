package com.shifterwebapp.shifter.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select AVG(r.rating) from Review r where r.enrollment.course.id = :courseId")
    Double findAverageRatingByCourse(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.course.id = :courseId")
    List<Review> findReviewsByCourse(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.payment.user.id = :userId")
    List<Review> findReviewsByUser(@Param("userId") Long userId);

    @Query("select r from Review r where r.enrollment.payment.user.id = :userId and r.enrollment.course.id = :courseId")
    Optional<Review> findReviewByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);


    @Query("select r from Review r where r.enrollment.id = :enrollmentId")
    Review findReviewByEnrollment(Long enrollmentId);

    @Query("select case when count(r) > 0 then true else false end" +
            " from Review r where r.enrollment.payment.user.id = :userId and r.enrollment.course.id = :userId")
    Boolean findHasBeenReviewedByUser(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
