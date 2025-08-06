package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.coursecontent.CourseContentDtoPreview;

import java.util.List;

public interface ImplCourseContentService {
    List<CourseContentDtoPreview> getCourseContentByCourseId(Long courseId);

}
