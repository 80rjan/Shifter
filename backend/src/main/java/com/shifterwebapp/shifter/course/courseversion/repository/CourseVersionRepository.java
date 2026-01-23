package com.shifterwebapp.shifter.course.courseversion.repository;


import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseVersionRepository extends JpaRepository<CourseVersion, Long> {

    public CourseVersion findByActiveTrueAndCourse_Id(Long courseId);
}
