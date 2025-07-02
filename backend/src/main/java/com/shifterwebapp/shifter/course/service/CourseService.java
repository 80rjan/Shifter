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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ImplCourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CourseContentMapper courseContentMapper;
    private final Validate validate;

    @Override
    public List<CourseDto> getAllCourses(Specification<Course> specification) {
        List<Course> courses = specification == null ?
                courseRepository.findAll() :
                courseRepository.findAll(specification);
        return courseMapper.toDto(courses);
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseMapper.toDto(course);
    }

    @Override
    public List<Interests> getAllTopics() {
        return courseRepository.getCourseTopics();
    }

    @Override
    public List<Skills> getAllSkills() {
        return courseRepository.getCourseSkills();
    }

    @Override
    public CourseDto createCourse(CourseDto request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .topic(request.getTopic())
                .difficulty(request.getDifficulty())
                .durationHours(request.getDurationHours())
                .price(request.getPrice())
                .rating(request.getRating())
                .ratingCount(request.getRatingCount())
                .descriptionShort(request.getDescriptionShort())
                .description(request.getDescription())
                .descriptionLong(request.getDescriptionLong())
                .skillsGained(request.getSkillsGained())
                .whatWillBeLearned(request.getWhatWillBeLearned())
//                .courseContents(courseContentMapper.toEntity(request.getCourseContents()))      // ??????
                .build();

        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDto> searchCoursesByTitle(String searchTitle) {
        List<Course> courses = courseRepository.findCoursesByTitle(searchTitle);
        return courseMapper.toDto(courses);
    }

    @Override
    public List<CourseDto> searchCoursesByTopic(String searchTopic) {
        List<Course> courses = courseRepository.findCoursesByTopic(searchTopic);
        return courseMapper.toDto(courses);
    }

    @Override
    public List<CourseDto> searchCoursesByDifficulties(List<Difficulty> searchDifficulties) {
        List<Course> courses = courseRepository.findCoursesByDifficultyIn(searchDifficulties);
        return courseMapper.toDto(courses);
    }

    @Override
    public List<CourseDto> searchCoursesByDurationHours(Float floorHours, Float ceilHours) {
        List<Course> courses = courseRepository.findCoursesByDurationHoursRange(floorHours, ceilHours);
        return courseMapper.toDto(courses);
    }

    @Override
    public List<CourseDto> searchCoursesBySkillsGained(List<Skills> searchSkills) {
        List<Course> courses = courseRepository.findCoursesBySkillsGainedIn(searchSkills);
        return courseMapper.toDto(courses);
    }
}
