package com.shifterwebapp.shifter.coursecontent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

    List<CourseContent> getCourseContentByCourse_Id(Long courseId);
}
