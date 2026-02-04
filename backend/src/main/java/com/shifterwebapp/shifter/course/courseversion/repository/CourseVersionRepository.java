package com.shifterwebapp.shifter.course.courseversion.repository;


import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseVersionRepository extends JpaRepository<CourseVersion, Long> {

    CourseVersion findByActiveTrueAndCourse_Id(Long courseId);

    List<CourseVersion> findByActiveTrueAndCourse_IdIn(List<Long> courseIds);
}
