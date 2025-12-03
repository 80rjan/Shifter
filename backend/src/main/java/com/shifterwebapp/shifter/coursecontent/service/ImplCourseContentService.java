package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.enums.Language;

import java.util.List;

public interface ImplCourseContentService {
    List<CourseContentDtoPreview> getCourseContentByCourseId(Long courseId, Language language);

}
