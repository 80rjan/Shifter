package com.shifterwebapp.shifter.courselecture.mapper;

import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoFull;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoLearn;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoPreview;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureTranslationStructureDto;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CourseLectureMapper {

    @Autowired
    protected UserCourseProgressMapper userCourseProgressMapper;

    public abstract CourseLectureDtoPreview toDtoPreview(CourseLecture courseLecture, @Context Language lang);

    public abstract List<CourseLectureDtoPreview> toDtoPreview(List<CourseLecture> courseLectures, @Context Language lang);

    @Mapping(target = "userCourseProgress", ignore = true)
    public abstract CourseLectureDtoLearn toDtoLearn(CourseLecture courseLecture, @Context Language lang, @Context Long userId);

    @Mapping(target = "userCourseProgress", ignore = true)
    public abstract List<CourseLectureDtoLearn> toDtoLearn(List<CourseLecture> courseLectures, @Context Language lang, @Context Long userId);

    public abstract CourseLectureDtoFull toDtoFull(CourseLecture courseLecture, @Context Language lang);

    public abstract List<CourseLectureDtoFull> toDtoFull(List<CourseLecture> courseLectures, @Context Language lang);

    @AfterMapping
    protected void applyTranslationPreview(CourseLecture courseLecture,
                                           @MappingTarget CourseLectureDtoPreview dto,
                                           @Context Language lang) {
        getTranslation(courseLecture, lang).ifPresent(t -> {
            dto.setTitle(t.getTitle());
            dto.setDescription(t.getDescription());
        });
    }

    @AfterMapping
    protected void applyTranslationLearn(CourseLecture courseLecture,
                                         @MappingTarget CourseLectureDtoLearn dto,
                                         @Context Language lang,
                                         @Context Long userId) {
        getTranslation(courseLecture, lang).ifPresent(t -> {
            dto.setTitle(t.getTitle());
            dto.setDescription(t.getDescription());
            dto.setContentText(t.getContentText());
        });

        getUserProgress(courseLecture, userId).ifPresent(progress ->
                dto.setUserCourseProgress(userCourseProgressMapper.toDto(progress))
        );
    }

    @AfterMapping
    protected void applyTranslationFull(CourseLecture courseLecture,
                                        @MappingTarget CourseLectureDtoFull dto,
                                        @Context Language lang) {
        getTranslation(courseLecture, lang).ifPresent(t -> {
            dto.setTitle(t.getTitle());
            dto.setDescription(t.getDescription());
            dto.setContentText(t.getContentText());
        });
    }

    protected Optional<CourseLectureTranslate> getTranslation(CourseLecture courseLecture, Language lang) {
        return courseLecture.getTranslations().stream()
                .filter(t -> t.getLanguage().equals(lang))
                .findFirst();
    }

    protected Optional<UserCourseProgress> getUserProgress(CourseLecture courseLecture, Long userId) {
        return courseLecture.getUserCourseProgress().stream()
                .filter(p -> p.getEnrollment().getUser().getId().equals(userId))
                .findFirst();
    }
}