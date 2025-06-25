package com.shifterwebapp.shifter.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select AVG(r.rating) from Review r where r.enrollment.course.id = :courseId")
    Float findAverageRatingByCourse(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.course.id = :courseId")
    List<Review> findReviewsByCourse(@Param("courseId") Long courseId);

    @Query("select r from Review r where r.enrollment.payment.account.id = :accountId")
    List<Review> findReviewsByAccount(@Param("accountId") Long accountId);

    @Query("select case when count(r) > 0 then true else false end" +
            " from Review r where r.enrollment.payment.account.id = :accountId and r.enrollment.course.id = :accountId")
    Boolean findHasBeenReviewedByAccount(@Param("accountId") Long accountId, @Param("courseId") Long courseId);
}
