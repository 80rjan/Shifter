package com.shifterwebapp.shifter.learning.application.impl;

import com.shifterwebapp.shifter.learning.web.request.ReviewRequest;
import com.shifterwebapp.shifter.learning.web.response.ReviewResponse;

import java.util.List;
import java.util.Map;

public interface ReviewServiceImpl {
    ReviewResponse getReviewById(Long id);
    List<ReviewResponse> getReviewsByCourse(Long courseId);
    List<ReviewResponse> getReviewsByUser(Long userId);
    ReviewResponse getReviewByUserAndCourse(Long userId, Long courseId);
    ReviewResponse getReviewByEnrollment(Long enrollmentId);
    Double getAverageRatingByCourse(Long courseId);
    Map<Long, Double> getAverageRatingByCourse(List<Long> courseIds);

    ReviewResponse writeReview(Long userId, Long courseId, ReviewRequest reviewRequest);

    ReviewResponse updateReview(Long userId, Long courseId, ReviewRequest reviewRequest);

    Boolean hasBeenReviewedByUser(Long userId, Long courseId);
}
