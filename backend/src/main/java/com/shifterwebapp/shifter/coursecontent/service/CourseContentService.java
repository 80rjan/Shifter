package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentDto;
import com.shifterwebapp.shifter.coursecontent.CourseContentMapper;
import com.shifterwebapp.shifter.coursecontent.CourseContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseContentService implements ImplCourseContentService {

    private final CourseContentRepository courseContentRepository;
    private final CourseContentMapper courseContentMapper;

    @Override
    public List<CourseContentDto> getCourseContentByCourseId(Long courseId) {
        List<CourseContent> courseContents = courseContentRepository.getCourseContentByCourse_Id(courseId);
        return courseContentMapper.toDto(courseContents);
    }
}
