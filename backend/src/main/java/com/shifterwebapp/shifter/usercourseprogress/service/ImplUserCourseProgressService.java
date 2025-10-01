package com.shifterwebapp.shifter.usercourseprogress.service;

import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;

import java.util.List;

public interface ImplUserCourseProgressService {

    List<UserCourseProgress> saveAllUserCourseProgress(List<UserCourseProgress> userCourseProgresses);

    UserCourseProgress completeUserCourseProgress(Long progressId, Long userId);

    UserCourseProgress uncompleteUserCourseProgress(Long progressId, Long userId);

    List<UserCourseProgress> getUserCourseProgressByEnrollment(Long enrollmentId);
    List<UserCourseProgress> getUserCourseProgressByEnrollmentAndCompletedTrue(Long enrollmentId);
}
