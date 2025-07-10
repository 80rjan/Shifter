package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.coursecontent.CourseContentMapper;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
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
    private final Validate validate;

    @Override
    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
        List<Course> courses = specification == null ?
                courseRepository.findAll() :
                courseRepository.findAll(specification);
        return courseMapperPreview.toDto(courses);
    }

    @Override
    public List<CourseDtoPreview> getRecommendedCourses(List<Skills> skills, List<Interests> interests) {
        List<Course> courses = courseRepository.findAll();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : courses) {
            boolean matchesSkills = course.getSkillsGained().stream().anyMatch(skills::contains);
            boolean matchesInterests = course.getWhatWillBeLearned().stream().anyMatch(interests::contains);

            int score = 0;
            if (matchesSkills && matchesInterests) {
                score += 2;
            } else if (matchesSkills || matchesInterests) {
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
    public List<Interests> getAllTopics() {
        return courseRepository.getCourseTopics();
    }

    @Override
    public List<Skills> getAllSkills() {
        return courseRepository.getCourseSkills();
    }
}
