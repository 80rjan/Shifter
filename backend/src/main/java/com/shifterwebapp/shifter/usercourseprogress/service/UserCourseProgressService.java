package com.shifterwebapp.shifter.usercourseprogress.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCourseProgressService implements ImplUserCourseProgressService {

    private final UserCourseProgressRepository userCourseProgressRepository;
    private final UserCourseProgressMapper userCourseProgressMapper;
    private final EnrollmentService enrollmentService;
    private final Validate validate;

    @Override
    public List<UserCourseProgress> getUserCourseProgressByEnrollment(Long enrollmentId) {
        return userCourseProgressRepository.findByEnrollmentId(enrollmentId);
    }

    @Override
    public UserCourseProgressDto completeUserCourseProgress(Long progressId, Long userId) {
        validate.validateUserCourseProgressExists(progressId);

        Long courseId = userCourseProgressRepository.getCourseId(progressId);

        boolean isUserEnrolledInCourse = enrollmentService.isUserEnrolledInCourse(userId, courseId);
        if (!isUserEnrolledInCourse) {
            throw new AccessDeniedException("User is not enrolled in the course with ID: " + courseId + " to update progress with ID: " + progressId);
        }

        UserCourseProgress userCourseProgress = userCourseProgressRepository.findById(progressId).orElseThrow();
        userCourseProgress.setCompleted(true);
        userCourseProgress.setCompletedAt(LocalDateTime.now());
        userCourseProgressRepository.save(userCourseProgress);

        return userCourseProgressMapper.toDto(userCourseProgress);
    }

    @Override
    public UserCourseProgressDto uncompleteUserCourseProgress(Long progressId, Long userId) {
        validate.validateUserCourseProgressExists(progressId);

        Long courseId = userCourseProgressRepository.getCourseId(progressId);

        boolean isUserEnrolledInCourse = enrollmentService.isUserEnrolledInCourse(userId, courseId);
        if (!isUserEnrolledInCourse) {
            throw new AccessDeniedException("User is not enrolled in the course with ID: " + courseId + " to update progress with ID: " + progressId);
        }

        UserCourseProgress userCourseProgress = userCourseProgressRepository.findById(progressId).orElseThrow();
        userCourseProgress.setCompleted(false);
        userCourseProgress.setCompletedAt(LocalDateTime.now());
        userCourseProgressRepository.save(userCourseProgress);

        return userCourseProgressMapper.toDto(userCourseProgress);
    }
}
