package com.shifterwebapp.shifter.courselecture.mapper;

import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoFull;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoLearn;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoPreview;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureTranslationStructureDto;
import com.shifterwebapp.shifter.enums.Language;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CourseLectureMapper {

    public abstract CourseLectureDtoPreview toDtoPreview(CourseLecture courseLecture, @Context Language lang);

    public abstract List<CourseLectureDtoPreview> toDtoPreview(List<CourseLecture> courseLectures, @Context Language lang);

    public abstract CourseLectureDtoLearn toDtoLearn(CourseLecture courseLecture, @Context Language lang);

    public abstract List<CourseLectureDtoLearn> toDtoLearn(List<CourseLecture> courseLectures, @Context Language lang);

    public abstract CourseLectureDtoFull toDtoFull(CourseLecture courseLecture, @Context Language lang);

    public abstract List<CourseLectureDtoFull> toDtoFull(List<CourseLecture> courseLectures, @Context Language lang);

    @AfterMapping
    protected void applyTranslation(CourseLecture courseLecture, @MappingTarget Object dto, @Context Language lang) {
        Optional<CourseLectureTranslate> translationOpt = courseLecture.getCourseLectureTranslates().stream().filter(t -> t.getLanguage().equals(lang)).findFirst();
        if (translationOpt.isEmpty()) {
            return;
        }
        CourseLectureTranslate t = translationOpt.get();
        if (dto instanceof CourseLectureDtoPreview d) {
            d.setTitle(t.getTitle());
            d.setDescription(t.getDescription());

        }
        if (dto instanceof CourseLectureDtoLearn d) {
            d.setTitle(t.getTitle());
            d.setDescription(t.getDescription());
            d.setContentText(t.getContentText());
        }
        if (dto instanceof CourseLectureDtoFull d) {
            d.setTitle(t.getTitle());
            d.setDescription(t.getDescription());
            d.setContentText(t.getContentText());
        }
    }
}