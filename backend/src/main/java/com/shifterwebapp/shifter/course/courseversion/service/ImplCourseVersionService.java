package com.shifterwebapp.shifter.course.courseversion.service;

import com.shifterwebapp.shifter.course.courseversion.CourseVersion;

import java.util.List;
import java.util.Map;

public interface ImplCourseVersionService {
    CourseVersion getActiveByCourseId(Long courseId);
    Map<Long, CourseVersion> getActiveByCourseIds(List<Long> courseIds);
}
