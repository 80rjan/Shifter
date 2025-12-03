package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentTranslate;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoBuilder;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoFull;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.coursecontent.repository.CourseContentRepository;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.service.CourseLectureService;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseContentService implements ImplCourseContentService {

    private final CourseContentRepository courseContentRepository;
    private final CourseContentDtoBuilder courseContentDtoBuilder;
    private final CourseLectureService courseLectureService;

    public CourseContent buildCourseContent(CourseContentDtoFull courseContentDtoFull, Course course, Language language) {
        CourseContent content = CourseContent.builder()
                .position(courseContentDtoFull.getPosition())
                .course(course)
                .build();

        CourseContentTranslate contentTranslate = CourseContentTranslate.builder()
                .title(courseContentDtoFull.getTitle())
                .language(language)
                .courseContent(content)
                .build();

        content.setCourseContentTranslates(List.of(contentTranslate));

        List<CourseLecture> lectureList = courseContentDtoFull.getCourseLectures().stream()
                .map(lecture -> courseLectureService.buildCourseLecture(lecture, content, language))
                .toList();

        content.setCourseLectures(lectureList);
        return content;
    }

    @Override
    public List<CourseContentDtoPreview> getCourseContentByCourseId(
            Long courseId,
            Language language
            ) {
        List<CourseContent> courseContents = courseContentRepository.getCourseContentByCourse_Id(courseId);
        return courseContentDtoBuilder.getCourseContentDtoPreview(courseContents, language);
    }
}
