package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentDtoPreview;
import com.shifterwebapp.shifter.coursecontent.CourseContentMapperPreview;
import com.shifterwebapp.shifter.coursecontent.CourseContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseContentService implements ImplCourseContentService {

    private final CourseContentRepository courseContentRepository;
    private final CourseContentMapperPreview courseContentMapperPreview;

    @Override
    public List<CourseContentDtoPreview> getCourseContentByCourseId(Long courseId) {
        List<CourseContent> courseContents = courseContentRepository.getCourseContentByCourse_Id(courseId);
        return courseContentMapperPreview.toDto(courseContents);
    }
}
