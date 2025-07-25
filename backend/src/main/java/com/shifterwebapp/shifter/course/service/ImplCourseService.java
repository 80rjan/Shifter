package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseDtoDetail;
import com.shifterwebapp.shifter.course.CourseDtoPreview;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ImplCourseService {
    List<CourseDtoPreview> getAllCourses(Specification<Course> specification);
    List<CourseDtoPreview> getRecommendedCourses(Long userId);
    List<CourseDtoPreview> getEnrolledCourses(Long userId);
    List<CourseDtoPreview> getTopRatedCourses();
    List<CourseDtoPreview> getMostPopularCourses();
    CourseDtoDetail getCourseById(Long id);
    Course getCourseEntityById(Long courseId);

    List<Interests> getAllTopics();
    List<Skills> getAllSkills();
}
