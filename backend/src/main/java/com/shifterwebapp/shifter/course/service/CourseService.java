package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.coursecontent.CourseContentMapper;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
        List<Course> courses = specification == null ?
                courseRepository.findAll() :
                courseRepository.findAll(specification);
        return courseMapperPreview.toDto(courses);
    }

    @Override
    public List<CourseDtoPreview> getRecommendedCourses(Long userId) {
        UserDto user = userService.getUserById(userId);
        List<Skills> skills = user.getSkills();
        List<Interests> interests = user.getInterests();

        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);

        List<Course> courses = courseRepository.findAll();

        List<Course> filteredCourses = courses
                .stream()
                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                .toList();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : filteredCourses) {
            boolean matchesSkills = course.getSkillsGained().stream().anyMatch(skills::contains);
            boolean matchesTopics = course.getTopicsCovered().stream().anyMatch(interests::contains);

            int score = 0;
            if (matchesSkills && matchesTopics) {
                score += 2;
            } else if (matchesSkills || matchesTopics) {
                score += 1;
            }

            if (score > 0)
                scoredCourses.add(new ScoredCourse(course, score));
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        if (scoredCourses.size() < 5) {
            courses.sort((a, b) -> Integer.compare(b.getRating()/b.getRatingCount(), a.getRating()/a.getRatingCount()));

            while (scoredCourses.size() < 5)
                scoredCourses.add(new ScoredCourse(courses.remove(0), 0));
        }

        return scoredCourses
                .subList(0, 5)
                .stream()
                .map(ScoredCourse::getCourse)
                .map(courseMapperPreview::toDto)
                .toList();
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
    public List<Interests> getAllTopics() {
        return courseRepository.getCourseTopics();
    }

    @Override
    public List<Skills> getAllSkills() {
        return courseRepository.getCourseSkills();
    }
}
