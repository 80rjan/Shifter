package com.shifterwebapp.shifter.courselecture.dto;

import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.mapper.CourseLectureMapper;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseLectureDtoBuilder {

    private final CourseLectureMapper courseLectureMapper;


    public CourseLectureDtoPreview getCourseLectureDtoPreview(CourseLecture courseLecture, Language language) {
        CourseLectureDtoPreview courseLectureDtoPreview = courseLectureMapper.toDtoPreview(courseLecture, language);
        return courseLectureDtoPreview;
    }

    public List<CourseLectureDtoPreview> getCourseLectureDtoPreview(List<CourseLecture> courseLectures, Language language) {
        List<CourseLectureDtoPreview> courseLectureDtoPreviewList = new ArrayList<>();
        for (CourseLecture courseLecture: courseLectures) {
            CourseLectureDtoPreview dto = getCourseLectureDtoPreview(courseLecture, language);

            courseLectureDtoPreviewList.add(dto);
        }

        return courseLectureDtoPreviewList;
    }

    public CourseLectureDtoLearn getCourseLectureDtoLearn(CourseLecture courseLecture, Language language) {
        CourseLectureDtoLearn courseLectureDtoLearn = courseLectureMapper.toDtoLearn(courseLecture, language);
        return courseLectureDtoLearn;
    }

    public List<CourseLectureDtoLearn> getCourseLectureDtoLearn(List<CourseLecture> courseLectures, Language language) {
        List<CourseLectureDtoLearn> courseLectureDtoLearnList = new ArrayList<>();
        for (CourseLecture courseLecture: courseLectures) {
            CourseLectureDtoLearn dto = getCourseLectureDtoLearn(courseLecture, language);

            courseLectureDtoLearnList.add(dto);
        }

        return courseLectureDtoLearnList;
    }
}
