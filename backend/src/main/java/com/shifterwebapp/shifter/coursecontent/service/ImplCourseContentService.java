package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.coursecontent.CourseContentDto;

import java.util.List;

public interface ImplCourseContentService {
    List<CourseContentDto> getCourseContentByCourseId(Long courseId);

}
