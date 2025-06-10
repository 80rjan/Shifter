package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.course.CourseDto;
import com.shifterwebapp.shifter.course.enums.Difficulty;
import com.shifterwebapp.shifter.user.enums.Skills;

import java.util.List;

public interface ImplCourseService {
    List<CourseDto> getAllCourses();
    CourseDto getCourseById(Long id);

    List<CourseDto> searchCoursesByTitle(String searchTitle);
    List<CourseDto> searchCoursesByTopic(String searchTopic);
    List<CourseDto> searchCoursesByDifficulty(Difficulty searchDifficulty);
    List<CourseDto> searchCoursesByPrice(Float floorPrice, Float ceilPrice);
    List<CourseDto> searchCoursesByDurationHours(Float floorHours, Float ceilHours);
    List<CourseDto> searchCoursesBySkillsGained(List<Skills> searchSkills);


}
