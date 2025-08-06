package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.mapper.CourseMapperDetail;
import com.shifterwebapp.shifter.course.mapper.CourseMapperPreview;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentRepository;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.CourseLectureRepository;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.upload.MetaInfo;
import com.shifterwebapp.shifter.upload.S3UploadResponse;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ImplCourseService {

    private final CourseRepository courseRepository;
    private final CourseMapperPreview courseMapperPreview;
    private final CourseMapperDetail courseMapperDetail;
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
                course.setImageUrl(s3UploadResponse.getUrl());
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

                        courseLecture.setContentStoragePath(s3UploadResponse.getUrl());
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
        return courseMapperPreview.toDto(courses);
    }

    @Override
    public List<CourseDtoPreview> getMostPopularCourses() {
        List<Course> courses = courseRepository.findCoursesOrderedByPopularity();
        return courseMapperPreview.toDto(courses);
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
