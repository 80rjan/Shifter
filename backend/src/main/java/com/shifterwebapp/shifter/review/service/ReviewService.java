package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.review.ReviewMapper;
import com.shifterwebapp.shifter.review.ReviewRepository;
import com.shifterwebapp.shifter.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements ImplReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final Validate validate;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " not found"));
        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Review> reviews = reviewRepository.findReviewsByCourse(courseId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    public List<ReviewDto> getReviewsByAccount(Long accountId) {
        validate.validateAccountExists(accountId);
        List<Review> reviews = reviewRepository.findReviewsByAccount(accountId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    public Float getAverageRatingByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        Float avgRating = reviewRepository.findAverageRatingByCourse(courseId);
        return avgRating != null ? avgRating : 0f;
    }

    @Override
    public ReviewDto writeReview(Long enrollmentId, ReviewDto reviewDto) {
        validate.validateEnrollmentExists(enrollmentId);

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        if (enrollment.getReview() != null) {
            throw new RuntimeException("Review already submitted for enrollment with ID " + enrollmentId + "!");
        }
        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.COMPLETED) {
            throw new RuntimeException("Cannot review a course that has not been completed by account!");
        }

        Review review = Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .canBeUsedAsTestimonial(reviewDto.getCanBeUsedAsTestimonial())
                .enrollment(enrollment)
                .date(new Date())
                .build();

        reviewRepository.save(review);

        return reviewMapper.toDto(review);
    }

    @Override
    public Boolean hasBeenReviewedByAccount(Long accountId, Long courseId) {
        validate.validateAccountExists(accountId);
        validate.validateCourseExists(courseId);
        return reviewRepository.findHasBeenReviewedByAccount(accountId, courseId);
    }
}
