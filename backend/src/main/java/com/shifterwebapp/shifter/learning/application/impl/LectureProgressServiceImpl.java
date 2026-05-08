package com.shifterwebapp.shifter.learning.application.impl;


import com.shifterwebapp.shifter.learning.domain.LectureProgress;

import java.util.List;

public interface LectureProgressServiceImpl {

    List<LectureProgress> saveAllUserCourseProgress(List<LectureProgress> lectureProgresses);

    LectureProgress completeUserCourseProgress(Long progressId, Long userId);

    LectureProgress uncompleteUserCourseProgress(Long progressId, Long userId);

    List<LectureProgress> getUserCourseProgressByEnrollment(Long enrollmentId);
    List<LectureProgress> getUserCourseProgressByEnrollmentAndCompletedTrue(Long enrollmentId);
    List<LectureProgress> getUserCourseProgressByEnrollmentsAndCompletedTrue(List<Long> enrollmentId);
}
