package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.review.ReviewRequest;

import java.util.List;

public interface ImplReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByCourse(Long courseId);
    List<ReviewDto> getReviewsByUser(Long userId);
    ReviewDto getReviewByUserAndCourse(Long userId, Long courseId);
    ReviewDto getReviewByEnrollment(Long enrollmentId);
    Double getAverageRatingByCourse(Long courseId);

    ReviewDto writeReview(Long userId, Long courseId, ReviewRequest reviewRequest);

    ReviewDto updateReview(Long userId, Long courseId, ReviewRequest reviewRequest);

    Boolean hasBeenReviewedByUser(Long userId, Long courseId);
}
