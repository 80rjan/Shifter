package com.shifterwebapp.shifter.coursecontent.repository;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

    @Query("select c from CourseContent c where c.courseVersion.course.id = :courseId")
    List<CourseContent> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c.courseVersion.course.id, COUNT(c) FROM CourseContent c GROUP BY c.courseVersion.course.id")
    List<Object[]> countCourseContentsPerCourse();
}
