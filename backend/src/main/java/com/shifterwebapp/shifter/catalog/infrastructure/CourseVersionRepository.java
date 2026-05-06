package com.shifterwebapp.shifter.catalog.infrastructure;


import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseVersionRepository extends JpaRepository<CourseVersion, Long> {

    CourseVersion findByActiveTrueAndCourse_Id(Long courseId);

    List<CourseVersion> findByActiveTrueAndCourse_IdIn(List<Long> courseIds);
}
