package com.shifterwebapp.shifter.catalog.infrastructure;

import com.shifterwebapp.shifter.catalog.domain.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

    @Query("select c from CourseModule c where c.courseVersion.course.id = :courseId")
    List<CourseModule> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c.courseVersion.course.id, COUNT(c) FROM CourseModule c GROUP BY c.courseVersion.course.id")
    List<Object[]> countCourseContentsPerCourse();
}
