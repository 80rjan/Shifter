package com.shifterwebapp.shifter.coursecontent.mapper;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentTranslate;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoFull;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoLearn;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.courselecture.mapper.CourseLectureMapper;
import com.shifterwebapp.shifter.enums.Language;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CourseLectureMapper.class})
public abstract class CourseContentMapper {

    public abstract CourseContentDtoPreview toDtoPreview(CourseContent courseContent, @Context Language lang);
    public abstract List<CourseContentDtoPreview> toDtoPreview(List<CourseContent> courseContents, @Context Language lang);

    public abstract CourseContentDtoLearn toDtoLearn(CourseContent courseContent, @Context Language lang, @Context Long userId);
    public abstract List<CourseContentDtoLearn> toDtoLearn(List<CourseContent> courseContents, @Context Language lang, @Context Long userId);

    public abstract CourseContentDtoFull toDtoFull(CourseContent courseContent, @Context Language lang);
    public abstract List<CourseContentDtoFull> toDtoFull(List<CourseContent> courseContents, @Context Language lang);

    @AfterMapping
    protected void applyTranslationPreview(@MappingTarget CourseContentDtoPreview dto,
                                           CourseContent courseContent,
                                           @Context Language lang) {
        getTranslation(courseContent, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
    }

    @AfterMapping
    protected void applyTranslationLearn(@MappingTarget CourseContentDtoLearn dto,
                                         CourseContent courseContent,
                                         @Context Language lang,
                                         @Context Long userId) {
        getTranslation(courseContent, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
    }

    @AfterMapping
    protected void applyTranslationFull(@MappingTarget CourseContentDtoFull dto,
                                        CourseContent courseContent,
                                        @Context Language lang) {
        getTranslation(courseContent, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
    }

    protected Optional<CourseContentTranslate> getTranslation(CourseContent courseContent, Language lang) {
        return courseContent.getTranslations().stream()
                .filter(t -> t.getLanguage().equals(lang))
                .findFirst();
    }
}
