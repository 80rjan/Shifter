package com.shifterwebapp.shifter.course.course.mapper;

import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.course.course.dto.*;
import com.shifterwebapp.shifter.coursecontent.mapper.CourseContentMapper;
import com.shifterwebapp.shifter.enums.Language;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CourseContentMapper.class})
public abstract class CourseMapper {
    public abstract CourseDtoPreview toDtoPreview(Course course, @Context Language lang);

    public abstract List<CourseDtoPreview> toDtoPreview(List<Course> courses, @Context Language lang);

    public abstract CourseDtoDetail toDtoDetail(Course course, @Context Language lang);

    public abstract List<CourseDtoDetail> toDtoDetail(List<Course> courses, @Context Language lang);

    public abstract CourseDtoPreviewEnrolled toDtoPreviewEnrolled(Course course, @Context Language lang);

    public abstract List<CourseDtoPreviewEnrolled> toDtoPreviewEnrolled(List<Course> courses, @Context Language lang);

    public abstract CourseDtoLearn toDtoLearn(Course course, @Context Language lang);

    public abstract List<CourseDtoLearn> toDtoLearn(List<Course> courses, @Context Language lang);

    public abstract CourseDtoFull toDtoFull(Course course, @Context Language lang);

    public abstract List<CourseDtoFull> toDtoFull(List<Course> courses, @Context Language lang);

    @AfterMapping
    protected void applyTranslation(Course course, @MappingTarget Object dto, @Context Language lang) {
        Optional<CourseTranslate> translationOpt = course.getTranslations().stream().filter(t -> t.getLanguage().equals(lang)).findFirst();
        if (translationOpt.isEmpty()) {
            return;
        }
        CourseTranslate t = translationOpt.get();
        if (dto instanceof CourseDtoPreview d) {
            d.setTitleShort(t.getTitleShort());
            d.setTitle(t.getTitle());
        }
        if (dto instanceof CourseDtoDetail d) {
            d.setTitleShort(t.getTitleShort());
            d.setTitle(t.getTitle());
            d.setDescriptionShort(t.getDescriptionShort());
            d.setDescription(t.getDescription());
            d.setDescriptionLong(t.getDescriptionLong());
            d.setWhatWillBeLearned(t.getWhatWillBeLearned());
        }
        if (dto instanceof CourseDtoPreviewEnrolled d) {
            d.setTitleShort(t.getTitleShort());
            d.setTitle(t.getTitle());
        }
        if (dto instanceof CourseDtoLearn d) {
            d.setTitleShort(t.getTitleShort());
            d.setTitle(t.getTitle());
        }
    }
}