package com.shifterwebapp.shifter.review;

import com.shifterwebapp.shifter.review.service.ReviewService;
import com.shifterwebapp.shifter.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<ReviewDto>> getReviewByCourse(@PathVariable Long courseId) {
        List<ReviewDto> reviewDtos = reviewService.getReviewsByCourse(courseId);
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewByUser(@PathVariable Long userId) {
        List<ReviewDto> reviewDtos = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviewDtos);
    }

    @PostMapping
    public ResponseEntity<?> writeReview(@RequestParam Long enrollmentId, @RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto savedReviewDto = reviewService.writeReview(enrollmentId, reviewDto);
            return ResponseEntity.ok(savedReviewDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
