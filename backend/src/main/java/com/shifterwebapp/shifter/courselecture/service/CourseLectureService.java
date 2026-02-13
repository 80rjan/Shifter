package com.shifterwebapp.shifter.courselecture.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoFull;
import com.shifterwebapp.shifter.courselecture.repository.CourseLectureRepository;
import com.shifterwebapp.shifter.courselecture.repository.CourseLectureTranslateRepository;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseLectureService implements ImplCourseLectureService{

    private final Validate validate;
    private final CourseLectureRepository courseLectureRepository;
    private final CourseLectureTranslateRepository courseLectureTranslateRepository;

    // No transactional needed here because this is a builder method
    public CourseLecture buildCourseLecture(CourseLectureDtoFull courseLectureDtoFull, CourseContent courseContent, Language language) {
        CourseLecture lecture = CourseLecture.builder()
                .durationMinutes(courseLectureDtoFull.getDurationMinutes())
                .position(courseLectureDtoFull.getPosition())
                .contentType(courseLectureDtoFull.getContentType())
                .courseContent(courseContent)
                .build();

        CourseLectureTranslate lectureTranslate = CourseLectureTranslate.builder()
                .language(language)
                .contentFileName(courseLectureDtoFull.getContentFileName())
                .title(courseLectureDtoFull.getTitle())
                .description(courseLectureDtoFull.getDescription())
                .contentText(courseLectureDtoFull.getContentText())
                .courseLecture(lecture)
                .build();

        lecture.setTranslations(List.of(lectureTranslate));

        return lecture;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseLectureTranslate getByCourseLectureIdAndLanguage(Long lectureId, Language language) {
        return courseLectureTranslateRepository.findByIdAndLanguage(lectureId, language);
    }
}
