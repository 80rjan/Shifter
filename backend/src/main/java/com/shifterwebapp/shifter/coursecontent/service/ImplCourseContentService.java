package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoFull;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.enums.Language;

import java.util.List;

public interface ImplCourseContentService {

    CourseContent buildCourseContent(CourseContentDtoFull courseContentDtoFull, CourseVersion courseVersion, Language language);

    List<CourseContentDtoPreview> getCourseContentByCourseId(Long courseId, Language language);
}
