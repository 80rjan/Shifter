package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.mapper.CourseMapperDetail;
import com.shifterwebapp.shifter.course.mapper.CourseMapperFull;
import com.shifterwebapp.shifter.course.mapper.CourseMapperPreview;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.upload.MetaInfo;
import com.shifterwebapp.shifter.upload.S3UploadResponse;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ImplCourseService {

    private final CourseRepository courseRepository;
    private final CourseMapperPreview courseMapperPreview;
    private final CourseMapperDetail courseMapperDetail;
    private final CourseMapperFull courseMapperFull;
    private final UserService userService;
    private final Validate validate;
    private final EnrollmentService enrollmentService;


    @Override
    public Course createCourse(String courseJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Course course = objectMapper.readValue(courseJson, Course.class);
        course.setRating(0);
        course.setRatingCount(0);

        for (CourseContent content : course.getCourseContents()) {
            content.setCourse(course);

            for (CourseLecture lecture : content.getCourseLectures()) {
                lecture.setCourseContent(content);
            }
        }

        return courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();

        for (S3UploadResponse s3UploadResponse : s3UploadResponses) {
            if ("COURSE_IMAGE".equals(s3UploadResponse.getType())) {
                course.setImageUrl(s3UploadResponse.getFileName());
                continue;
            }

            MetaInfo meta = s3UploadResponse.getMeta();
            Integer contentPosition = meta.getContentPosition();
            Integer lecturePosition = meta.getLecturePosition();

            if (contentPosition != null && lecturePosition != null) {
                int contentIndex = contentPosition - 1;
                int lectureIndex = lecturePosition - 1;

                if (contentIndex >= 0 && contentIndex < course.getCourseContents().size()) {
                    var courseContent = course.getCourseContents().get(contentIndex);
                    if (lectureIndex >= 0 && lectureIndex < courseContent.getCourseLectures().size()) {
                        CourseLecture courseLecture = courseContent.getCourseLectures().get(lectureIndex);

                        courseLecture.setContentFileName(s3UploadResponse.getFileName());
                    } else {
                        // handle invalid lecture index
                        System.err.println("Invalid lecture index: " + lectureIndex);
                    }
                } else {
                    // handle invalid content index
                    System.err.println("Invalid content index: " + contentIndex);
                }
            }
        }
        return courseRepository.save(course);
    }

    @Override
    public Boolean lectureFileExistsInCourse(Long courseId, String fileName) {
        return courseRepository.lectureFileExistsInCourse(courseId, fileName);
    }

    @Override
    public CourseDtoFull getEnrolledCourseById(Long courseId, Long userId) {
        validate.validateCourseExists(courseId);

        boolean isUserEnrolled = enrollmentService.isUserEnrolledInCourse(userId, courseId);
        if (!isUserEnrolled) {
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the full course with its content!");
        }

        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseMapperFull.toDto(course);
    }

    @Override
    public List<CourseDtoPreview> getAllCourses(List<Long> courseIds) {
        List<Course> courses = courseIds != null && !courseIds.isEmpty() ?
                courseRepository.findCoursesByIdNotIn(courseIds) :
                courseRepository.findAll();
        return courseMapperPreview.toDto(courses);
    }

//    @Override
//    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
//        List<Course> courses = specification == null ?
//                courseRepository.findAll() :
//                courseRepository.findAll(specification);
//        return courseMapperPreview.toDto(courses);
//    }

    @Override
    public List<CourseDtoPreview> getRecommendedCourses(Long userId) {
        UserDto user = userService.getUserById(userId);
        List<String> skills = user.getSkills();
        List<String> interests = user.getInterests();

        // get user enrollments
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);

        // filter out enrolled courses
        List<Course> courses = courseRepository.findAll()
                .stream()
                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                .toList();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : courses) {
            boolean matchesSkills = course.getSkillsGained().stream().anyMatch(skills::contains);
            boolean matchesTopics = course.getTopicsCovered().stream().anyMatch(interests::contains);

            int score = 0;
            if (matchesSkills && matchesTopics) {
                score += 2;
            } else if (matchesSkills || matchesTopics) {
                score += 1;
            }

            scoredCourses.add(new ScoredCourse(course, score));
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));  // descending order

        int limit = Math.min(5, scoredCourses.size());
        return scoredCourses
                .subList(0, limit)
                .stream()
                .map(ScoredCourse::getCourse)
                .map(courseMapperPreview::toDto)
                .toList();
    }

    @Override
    public List<CourseDtoPreview> getEnrolledCourses(Long userId) {
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
        List<Course> courses = courseRepository.findAllById(enrolledCourseIds);
        return courseMapperPreview.toDto(courses);
    }


    @Override
    public List<CourseDtoPreview> getTopRatedCourses() {
        List<Course> courses = courseRepository.findCoursesOrderedByRating();
        int limit = Math.min(5, courses.size());
        return courseMapperPreview.toDto(
                courses
                        .subList(0, limit)
        );
    }

    @Override
    public List<CourseDtoPreview> getMostPopularCourses() {
        List<Course> courses = courseRepository.findCoursesOrderedByPopularity();
        int limit = Math.min(5, courses.size());
        return courseMapperPreview.toDto(
                courses
                        .subList(0, limit)
        );
    }


    @Override
    public CourseDtoDetail getCourseById(Long courseId) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseMapperDetail.toDto(course);
    }

    @Override
    public Course getCourseEntityById(Long courseId) {
        validate.validateCourseExists(courseId);
        return courseRepository.findById(courseId).orElseThrow();
    }

    @Override
    public List<String> getAllTopics() {
        return courseRepository.getCourseTopics();
    }

    @Override
    public List<String> getAllSkills() {
        return courseRepository.getCourseSkills();
    }


}
