package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.review.ReviewDto;

import java.util.List;

public interface ImplReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByCourse(Long courseId);
    List<ReviewDto> getReviewsByUser(Long userId);
    Double getAverageRatingByCourse(Long courseId);

    ReviewDto writeReview(Long enrollmentId, ReviewDto reviewDto);

    Boolean hasBeenReviewedByUser(Long userId, Long courseId);
}
