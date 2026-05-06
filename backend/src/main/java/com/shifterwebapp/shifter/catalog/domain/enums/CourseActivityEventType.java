package com.shifterwebapp.shifter.catalog.domain.enums;

public enum CourseActivityEventType {
    // Core engagement
    COURSE_VIEWED,           // User visited course detail page
    COURSE_ENROLLED,         // Enrolled in course
    COURSE_STARTED,          // First lesson/module accessed
    COURSE_COMPLETED,        // Finished entire course
    COURSE_ABANDONED,        // Stopped mid-way (e.g., no activity for 30 days)

    // Learning progress
    LESSON_STARTED,          // Began specific lesson
    LESSON_COMPLETED,        // Finished lesson
    MODULE_COMPLETED,        // Finished module/section
    QUIZ_ATTEMPTED,          // Started quiz/assessment
    QUIZ_PASSED,             // Passed quiz
    QUIZ_FAILED,            // Failed quiz (might retry)

    // Engagement depth
    VIDEO_WATCHED,           // Watched video content
    RESOURCE_DOWNLOADED,     // Downloaded course materials
    EXERCISE_SUBMITTED,      // Submitted practice work
    DISCUSSION_PARTICIPATED, // Posted in course forum/comments

    // Interest signals
    COURSE_WISHLISTED,       // Added to wishlist/saved
    COURSE_SHARED,           // Shared with others
    CERTIFICATE_DOWNLOADED,  // Downloaded completion certificate
    COURSE_REVIEWED,         // Left rating/review

    // Negative signals
    COURSE_UNENROLLED,       // Dropped the course
    LESSON_SKIPPED          // Skipped without completing
}
