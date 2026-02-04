package com.shifterwebapp.shifter.coursecontent.service;

import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentTranslate;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoFull;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.coursecontent.mapper.CourseContentMapper;
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
    private final CourseLectureService courseLectureService;
    private final CourseContentMapper courseContentMapper;

    @Override
    public CourseContent buildCourseContent(CourseContentDtoFull courseContentDtoFull, CourseVersion courseVersion, Language language) {
        CourseContent content = CourseContent.builder()
                .position(courseContentDtoFull.getPosition())
                .courseVersion(courseVersion)
                .build();

        CourseContentTranslate contentTranslate = CourseContentTranslate.builder()
                .title(courseContentDtoFull.getTitle())
                .language(language)
                .courseContent(content)
                .build();

        content.setTranslations(List.of(contentTranslate));

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
        List<CourseContent> courseContents = courseContentRepository.findByCourseId(courseId);
        return courseContentMapper.toDtoPreview(courseContents, language);
    }
}
