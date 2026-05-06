//package com.shifterwebapp.shifter.course.controllers;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.catalog.application.LectureProgressService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${api.base.path}/progress")
//public class LectureProgressController {
//
//    private final LectureProgressService userCourseProgressService;
//    private final Validate validate;
//
//    @PutMapping("{progressId}/complete")
//    public ResponseEntity<?> completeUserCourseProgress(
//            @PathVariable Long progressId,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        userCourseProgressService.completeUserCourseProgress(progressId, userId);
//
//        return ResponseEntity.ok("Successfully completed progress with ID: " + progressId + " for user with ID: " + userId);
//    }
//
//    @PutMapping("{progressId}/uncomplete")
//    public ResponseEntity<?> uncompleteUserCourseProgress(
//            @PathVariable Long progressId,
//            Authentication authentication
//    ) {
//        Long userId = validate.extractUserId(authentication);
//
//        userCourseProgressService.uncompleteUserCourseProgress(progressId, userId);
//
//        return ResponseEntity.ok("Successfully uncompleted progress with ID: " + progressId + " for user with ID: " + userId);
//    }
//}
