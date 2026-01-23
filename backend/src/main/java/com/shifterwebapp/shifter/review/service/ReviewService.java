package com.shifterwebapp.shifter.review.service;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.repository.EnrollmentRepository;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import com.shifterwebapp.shifter.review.*;
import com.shifterwebapp.shifter.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<ReviewDto> getReviewsByUser(Long userId) {
        validate.validateUserExists(userId);
        List<Review> reviews = reviewRepository.findReviewsByUser(userId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    public ReviewDto getReviewByUserAndCourse(Long userId, Long courseId) {
        validate.validateCourseExists(courseId);
        validate.validateUserExists(userId);
        Optional<Review> reviewOpt = reviewRepository.findReviewByUserAndCourse(userId, courseId);
        if (reviewOpt.isEmpty()) {
            throw new ResourceNotFoundException("Review not found for user with ID " + userId + " and course with ID " + courseId);
        }
        Review review = reviewOpt.get();
        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto getReviewByEnrollment(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);
        Review review = reviewRepository.findReviewByEnrollment(enrollmentId);
        return reviewMapper.toDto(review);
    }

    @Override
    public Double getAverageRatingByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        Double avgRating = reviewRepository.findAverageRatingByCourse(courseId);
        return avgRating != null ? avgRating : 0f;
    }

    @Override
    public ReviewDto writeReview(Long userId, Long courseId, ReviewRequest reviewRequest) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByUserAndCourse(userId, courseId);
        if (enrollment.getReview() != null) {
            throw new IllegalStateException("Review already submitted for course with ID " + courseId + "!");
        }
        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.COMPLETED) {
            throw new AccessDeniedException("Cannot review a course that has not been completed by user!");
        }

        Review review = Review.builder()
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .enrollment(enrollment)
                .reviewDate(LocalDate.now())
                .build();

        reviewRepository.save(review);

        return reviewMapper.toDto(review);
    }

    @Override
    public ReviewDto updateReview(Long userId, Long courseId, ReviewRequest reviewRequest) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByUserAndCourse(userId, courseId);
        if (enrollment.getReview() == null) {
            throw new IllegalStateException("Review hasn't been submitted for course with ID " + courseId + " so that it can be updated!");
        }
        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.COMPLETED) {
            throw new AccessDeniedException("Cannot review a course that has not been completed by user!");
        }

        Review review = reviewRepository.findReviewByEnrollment(enrollment.getId());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setReviewDate(LocalDate.now());

        reviewRepository.save(review);

        return reviewMapper.toDto(review);
    }

    @Override
    public Boolean hasBeenReviewedByUser(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);
        return reviewRepository.findHasBeenReviewedByUser(userId, courseId);
    }
}
