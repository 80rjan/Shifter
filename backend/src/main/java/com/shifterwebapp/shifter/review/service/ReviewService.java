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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements ImplReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final Validate validate;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " not found"));
        return reviewMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        List<Review> reviews = reviewRepository.findByCourseId(courseId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByUser(Long userId) {
        validate.validateUserExists(userId);
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getReviewByUserAndCourse(Long userId, Long courseId) {
        validate.validateCourseExists(courseId);
        validate.validateUserExists(userId);
        Optional<Review> reviewOpt = reviewRepository.findByUserIdAndCourseId(userId, courseId);
        if (reviewOpt.isEmpty()) {
            throw new ResourceNotFoundException("Review not found for user with ID " + userId + " and course with ID " + courseId);
        }
        Review review = reviewOpt.get();
        return reviewMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getReviewByEnrollment(Long enrollmentId) {
        validate.validateEnrollmentExists(enrollmentId);
        Review review = reviewRepository.findByEnrollmentId(enrollmentId);
        return reviewMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRatingByCourse(Long courseId) {
        validate.validateCourseExists(courseId);
        Double avgRating = reviewRepository.findAverageRatingByCourseId(courseId);
        return avgRating != null ? avgRating : 0f;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Double> getAverageRatingByCourse(List<Long> courseIds) {
//        validate.validateCourseExists(courseId);
        return reviewRepository.findByCourseIdIn(courseIds)
                .stream()
                .collect(
                        Collectors.groupingBy(
                                r -> r.getEnrollment().getCourseVersion().getCourse().getId(),
                                Collectors.averagingDouble(Review::getRating)
                        )
                );
    }

    @Override
    @Transactional
    public ReviewDto writeReview(Long userId, Long courseId, ReviewRequest reviewRequest) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
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
    @Transactional
    public ReviewDto updateReview(Long userId, Long courseId, ReviewRequest reviewRequest) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        if (enrollment.getReview() == null) {
            throw new IllegalStateException("Review hasn't been submitted for course with ID " + courseId + " so that it can be updated!");
        }
        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.COMPLETED) {
            throw new AccessDeniedException("Cannot review a course that has not been completed by user!");
        }

        Review review = reviewRepository.findByEnrollmentId(enrollment.getId());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setReviewDate(LocalDate.now());

        reviewRepository.save(review);

        return reviewMapper.toDto(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasBeenReviewedByUser(Long userId, Long courseId) {
        validate.validateUserExists(userId);
        validate.validateCourseExists(courseId);
        return reviewRepository.findHasBeenReviewedByUserIdAndCourseId(userId, courseId);
    }
}
