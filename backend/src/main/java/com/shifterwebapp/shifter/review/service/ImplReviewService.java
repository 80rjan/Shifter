package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.review.ReviewDto;

import java.util.List;

public interface ImplReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByCourse(Long courseId);
    List<ReviewDto> getReviewsByAccount(Long accountId);
    Float getAverageRatingByCourse(Long courseId);

    ReviewDto writeReview(Long enrollmentId, ReviewDto reviewDto);

    Boolean hasBeenReviewedByAccount(Long accountId, Long courseId);
}
