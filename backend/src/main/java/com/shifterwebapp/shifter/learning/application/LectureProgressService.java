//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.learning.application.EnrollmentService;
//import com.shifterwebapp.shifter.shared.exception.AccessDeniedException;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import com.shifterwebapp.shifter.catalog.infrastructure.LectureProgressRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class LectureProgressService implements ImplLectureProgressService {
//
//    private final LectureProgressRepository lectureProgressRepository;
//    private final EnrollmentService enrollmentService;
//    private final Validate validate;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<LectureProgress> getUserCourseProgressByEnrollment(Long enrollmentId) {
//        return lectureProgressRepository.findByEnrollmentId(enrollmentId);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<LectureProgress> getUserCourseProgressByEnrollmentAndCompletedTrue(Long enrollmentId) {
//        return lectureProgressRepository.findByEnrollmentIdAndCompletedTrue(enrollmentId);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<LectureProgress> getUserCourseProgressByEnrollmentsAndCompletedTrue(List<Long> enrollmentIds) {
//        return lectureProgressRepository.findByEnrollmentIdsAndCompletedTrue(enrollmentIds);
//    }
//
//    @Override
//    @Transactional
//    public List<LectureProgress> saveAllUserCourseProgress(List<LectureProgress> lectureProgresses) {
//        return lectureProgressRepository.saveAll(lectureProgresses);
//    }
//
//    @Override
//    @Transactional
//    public LectureProgress completeUserCourseProgress(Long progressId, Long userId) {
//        validate.validateUserCourseProgressExists(progressId);
//
//        Long courseId = lectureProgressRepository.getCourseId(progressId);
//        Long enrollmentId = lectureProgressRepository.getEnrollmentId(progressId);
//
//        boolean isUserEnrolledInCourse = enrollmentService.isUserEnrolledInCourse(userId, courseId);
//        if (!isUserEnrolledInCourse) {
//            throw new AccessDeniedException("User is not enrolled in the course with ID: " + courseId + " to update progress with ID: " + progressId);
//        }
//
//        LectureProgress lectureProgress = lectureProgressRepository.findById(progressId).orElseThrow();
//        lectureProgress.setCompleted(true);
//        lectureProgress.setCompletedAt(LocalDateTime.now());
//        lectureProgressRepository.save(lectureProgress);
//        lectureProgressRepository.flush();
//
//        enrollmentService.updateEnrollmentStatusToCompleted(enrollmentId);
//
//        return lectureProgress;
//    }
//
//    @Override
//    @Transactional
//    public LectureProgress uncompleteUserCourseProgress(Long progressId, Long userId) {
//        validate.validateUserCourseProgressExists(progressId);
//
//        Long courseId = lectureProgressRepository.getCourseId(progressId);
//
//        boolean isUserEnrolledInCourse = enrollmentService.isUserEnrolledInCourse(userId, courseId);
//        if (!isUserEnrolledInCourse) {
//            throw new AccessDeniedException("User is not enrolled in the course with ID: " + courseId + " to update progress with ID: " + progressId);
//        }
//
//        LectureProgress lectureProgress = lectureProgressRepository.findById(progressId).orElseThrow();
//        lectureProgress.setCompleted(false);
//        lectureProgress.setCompletedAt(LocalDateTime.now());
//        lectureProgressRepository.save(lectureProgress);
//
//        return lectureProgress;
//    }
//
//
//}
