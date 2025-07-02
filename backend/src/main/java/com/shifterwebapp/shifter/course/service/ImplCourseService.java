package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseDto;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ImplCourseService {
    List<CourseDto> getAllCourses(Specification<Course> specification);
    CourseDto getCourseById(Long id);

    List<Interests> getAllTopics();
    List<Skills> getAllSkills();

    CourseDto createCourse(CourseDto request);

    List<CourseDto> searchCoursesByTitle(String searchTitle);
    List<CourseDto> searchCoursesByTopic(String searchTopic);
    List<CourseDto> searchCoursesByDifficulties(List<Difficulty> searchDifficulty);
    List<CourseDto> searchCoursesByDurationHours(Float floorHours, Float ceilHours);
    List<CourseDto> searchCoursesBySkillsGained(List<Skills> searchSkills);


}
