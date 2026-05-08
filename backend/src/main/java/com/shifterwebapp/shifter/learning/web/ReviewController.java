//package com.shifterwebapp.shifter.review;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.learning.application.ReviewService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("${api.base.path}/review")
//public class ReviewController {
//
//    private final ReviewService reviewService;
//    private final Validate validate;
//
//    @GetMapping("/{reviewId}")
//    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long reviewId) {
//        ReviewResponse reviewDto = reviewService.getReviewById(reviewId);
//        return ResponseEntity.ok(reviewDto);
//    }
//
//    @GetMapping("/course/{courseId}")
//    public ResponseEntity<ReviewResponse> getReviewByCourse(
//            @PathVariable Long courseId,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//        ReviewResponse reviewDtos = reviewService.getReviewByUserAndCourse(userId, courseId);
//        return ResponseEntity.ok(reviewDtos);
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<ReviewResponse>> getReviewByUser(@PathVariable Long userId) {
//        List<ReviewResponse> reviewDtos = reviewService.getReviewsByUser(userId);
//        return ResponseEntity.ok(reviewDtos);
//    }
//
//    @PostMapping("/{courseId}")
//    public ResponseEntity<?> writeReview(
//            @PathVariable Long courseId,
//            @RequestBody ReviewRequest reviewRequest,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        reviewService.writeReview(
//                userId,
//                courseId,
//                reviewRequest
//        );
//
//        return ResponseEntity.ok("Successfully created review");
//    }
//
//    @PutMapping("/{courseId}")
//    public ResponseEntity<?> updateReview(
//            @PathVariable Long courseId,
//            @RequestBody ReviewRequest reviewRequest,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        reviewService.updateReview(
//                userId,
//                courseId,
//                reviewRequest
//        );
//
//        return ResponseEntity.ok("Successfully created review");
//    }
//}
