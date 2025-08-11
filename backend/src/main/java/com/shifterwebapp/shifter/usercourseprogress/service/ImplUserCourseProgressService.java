package com.shifterwebapp.shifter.usercourseprogress.service;

import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;

import java.util.List;

public interface ImplUserCourseProgressService {

    UserCourseProgressDto completeUserCourseProgress(Long progressId, Long userId);

    UserCourseProgressDto uncompleteUserCourseProgress(Long progressId, Long userId);

    List<UserCourseProgress> getUserCourseProgressByEnrollment(Long enrollmentId);
}
