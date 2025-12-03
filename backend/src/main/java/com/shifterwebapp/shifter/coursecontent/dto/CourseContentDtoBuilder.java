package com.shifterwebapp.shifter.coursecontent.dto;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.mapper.CourseContentMapper;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseContentDtoBuilder {

    private final CourseContentMapper courseContentMapper;


    public CourseContentDtoPreview getCourseContentDtoPreview(CourseContent courseContent, Language language) {
        CourseContentDtoPreview courseDtoPreview = courseContentMapper.toDtoPreview(courseContent, language);
        return courseDtoPreview;
    }

    public List<CourseContentDtoPreview> getCourseContentDtoPreview(List<CourseContent> courseContents, Language language) {
        List<CourseContentDtoPreview> courseContentDtoPreviewList = new ArrayList<>();
        for (CourseContent courseContent : courseContents) {
            CourseContentDtoPreview dto = getCourseContentDtoPreview(courseContent, language);

            courseContentDtoPreviewList.add(dto);
        }

        return courseContentDtoPreviewList;
    }

    public CourseContentDtoLearn getCourseContentDtoLearn(CourseContent courseContent, Language language) {
        CourseContentDtoLearn courseContentDtoLearn = courseContentMapper.toDtoLearn(courseContent, language);
        return courseContentDtoLearn;
    }

    public List<CourseContentDtoLearn> getCourseContentDtoLearn(List<CourseContent> courseContents, Language language) {
        List<CourseContentDtoLearn> courseContentDtoLearnList = new ArrayList<>();
        for (CourseContent courseContent: courseContents) {
            CourseContentDtoLearn dto = getCourseContentDtoLearn(courseContent, language);

            courseContentDtoLearnList.add(dto);
        }

        return courseContentDtoLearnList;
    }
}
