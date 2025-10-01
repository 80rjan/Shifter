package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreviewEnrolled;
import com.shifterwebapp.shifter.course.mapper.CourseMapper;
import com.shifterwebapp.shifter.coursecontent.CourseContentDtoFull;
import com.shifterwebapp.shifter.courselecture.CourseLectureDtoFull;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.review.service.ReviewService;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import com.shifterwebapp.shifter.usercourseprogress.service.UserCourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseDtoBuilder {

    private final ReviewService reviewService;
    private final EnrollmentService enrollmentService;
    private final UserCourseProgressService userCourseProgressService;
    private final CourseMapper courseMapper;
    private final UserCourseProgressMapper userCourseProgressMapper;

    private <T extends CourseDtoPreview> T enrichCourseDto(Course course, T dto) {
        Double rating = reviewService.getAverageRatingByCourse(course.getId());
        int contentCount = course.getCourseContents() != null ? course.getCourseContents().size() : 0;
        int lectureCount = course.getCourseContents() != null
                ? course.getCourseContents().stream()
                .mapToInt(content -> content.getCourseLectures() != null
                        ? content.getCourseLectures().size() : 0)
                .sum()
                : 0;

        dto.setAverageRating(rating != null ? rating : 0.0);
        dto.setCourseContentCount(contentCount);
        dto.setCourseLectureCount(lectureCount);

        return dto;
    }


    public CourseDtoPreview getCourseDtoPreview(Course course) {
        CourseDtoPreview courseDtoPreview = courseMapper.toDtoPreview(course);
        return enrichCourseDto(course, courseDtoPreview);
    }

    public List<CourseDtoPreview> getCourseDtoPreview(List<Course> courses) {
        List<CourseDtoPreview> courseDtoPreviewList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoPreview dto = getCourseDtoPreview(course);

            courseDtoPreviewList.add(dto);
        }

        return courseDtoPreviewList;
    }

    public CourseDtoDetail getCourseDtoDetail(Course course) {
        CourseDtoDetail courseDtoDetail = courseMapper.toDtoDetail(course);
        return enrichCourseDto(course, courseDtoDetail);
    }

    public List<CourseDtoDetail> getCourseDtoDetail(List<Course> courses) {
        List<CourseDtoDetail> courseDtoDetailList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoDetail dto = getCourseDtoDetail(course);

            courseDtoDetailList.add(dto);
        }

        return courseDtoDetailList;
    }

    public CourseDtoPreviewEnrolled getCourseDtoPreviewEnrolled(Course course, Long userId) {
        CourseDtoPreviewEnrolled courseDtoEnrolled = courseMapper.toDtoEnrolled(course);
        Enrollment enrollment = enrollmentService.getEnrollmentByUserAndCourse(userId, courseDtoEnrolled.getId());
        List<UserCourseProgress> userCourseProgress = userCourseProgressService.getUserCourseProgressByEnrollmentAndCompletedTrue(enrollment.getId());
        Integer reviewRating = 0;
        if (reviewService.hasBeenReviewedByUser(userId, course.getId()))
            reviewRating = reviewService.getReviewByUserAndCourse(userId, course.getId()).getRating();

        courseDtoEnrolled.setLecturesFinishedCount(userCourseProgress.size());
        courseDtoEnrolled.setRating(reviewRating);
        courseDtoEnrolled.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);

        return enrichCourseDto(course, courseDtoEnrolled);
    }

    public List<CourseDtoPreviewEnrolled> getCourseDtoPreviewEnrolled(List<Course> courses, Long userId) {
        List<CourseDtoPreviewEnrolled> courseDtoPreviewEnrolledList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoPreviewEnrolled dto = getCourseDtoPreviewEnrolled(course, userId);

            courseDtoPreviewEnrolledList.add(dto);
        }

        return courseDtoPreviewEnrolledList;
    }

    public CourseDtoFull getCourseDtoFull(Course course, Enrollment enrollment) {
        List<UserCourseProgress> userCourseProgress = userCourseProgressService.getUserCourseProgressByEnrollment(enrollment.getId());

        Integer reviewRating = reviewService.getReviewByEnrollment(enrollment.getId()).getRating();

        Map<Long, UserCourseProgress> progressMap = userCourseProgress.stream()
                .collect(Collectors.toMap(
                        UserCourseProgress::getCourseLectureId,
                        Function.identity()
                ));

        CourseDtoFull courseDto = courseMapper.toDtoFull(course);

        courseDto.setRating(reviewRating != null ? reviewRating : 0);
        courseDto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);
        for (CourseContentDtoFull contentDto : courseDto.getCourseContents()) {
            for (CourseLectureDtoFull lectureDto : contentDto.getCourseLectures()) {
                UserCourseProgress progress = progressMap.get(lectureDto.getId());
                lectureDto.setUserCourseProgress(userCourseProgressMapper.toDto(progress));
            }
        }

        return courseDto;
    }

    public List<CourseDtoFull> getCourseDtoFull(List<Course> courses, Enrollment enrollment) {
        List<CourseDtoFull> courseDtoFullList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoFull dto = getCourseDtoFull(course, enrollment);

            courseDtoFullList.add(dto);
        }

        return courseDtoFullList;
    }
}
