package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.review.ReviewDto;

import java.util.List;

public interface ImplReviewService {
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getReviewsByCourse(Long courseId);
    List<ReviewDto> getReviewsByUser(Long userId);
    Float getAverageRatingByCourse(Long courseId);

    Boolean hasBeenReviewedByUser(Long userId);
}
