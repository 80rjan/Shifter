package com.shifterwebapp.shifter.catalog.application.impl;

import com.shifterwebapp.shifter.catalog.domain.CourseVersion;

import java.util.List;
import java.util.Map;

public interface ImplCourseVersionService {
    CourseVersion getActiveByCourseId(Long courseId);
    Map<Long, CourseVersion> getActiveByCourseIds(List<Long> courseIds);
}
