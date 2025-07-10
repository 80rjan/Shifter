package com.shifterwebapp.shifter.unittests;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.review.ReviewMapper;
import com.shifterwebapp.shifter.review.ReviewRepository;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.review.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestReviewService {

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewMapper reviewMapper;
    @Mock
    Validate validate;
    @InjectMocks
    ReviewService reviewService;

    Review review;

    @BeforeEach
    public void setUp() {
        review = Review.builder()
                .id(1L)
                .rating(5)
                .comment("Comment")
                .canBeUsedAsTestimonial(true)
                .date(new Date())
                .enrollment(new Enrollment())
                .build();
    }

    @Test
    public void test_getReviewById() {
        ReviewDto dto = new ReviewDto();
        dto.setId(1L);
        dto.setRating(5);
        dto.setComment("Comment");
        dto.setCanBeUsedAsTestimonial(true);

        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Mockito.when(reviewMapper.toDto(review)).thenReturn(dto);

        ReviewDto result = reviewService.getReviewById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(5, result.getRating());
        Assertions.assertEquals("Comment", result.getComment());
        Assertions.assertEquals(true, result.getCanBeUsedAsTestimonial());
    }

    @Test
    public void test_getAverageRatingByCourse() {
        Long courseId = 1L;

        Mockito.when(reviewRepository.findAverageRatingByCourse(courseId)).thenReturn(5.0);
        Mockito.doNothing().when(validate).validateCourseExists(courseId);

        Double result = reviewService.getAverageRatingByCourse(courseId);
        Assertions.assertEquals(result, 5F);
    }

    @Test
    public void test_hasBeenReviewedByUser() {
        Long courseId = 1L;
        Long userId = 1L;

        Mockito.when(reviewRepository.findHasBeenReviewedByUser(userId, courseId)).thenReturn(true);
        Mockito.doNothing().when(validate).validateUserExists(userId);
        Mockito.doNothing().when(validate).validateCourseExists(courseId);

        Boolean result = reviewService.hasBeenReviewedByUser(userId, courseId);
        Assertions.assertEquals(true, result);
    }
}
