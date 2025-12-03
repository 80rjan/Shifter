package com.shifterwebapp.shifter.coursecontent.repository;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

    List<CourseContent> getCourseContentByCourse_Id(Long courseId);

    @Query("SELECT c.course.id, COUNT(c) FROM CourseContent c GROUP BY c.course.id")
    List<Object[]> countCourseContentsPerCourse();
}
