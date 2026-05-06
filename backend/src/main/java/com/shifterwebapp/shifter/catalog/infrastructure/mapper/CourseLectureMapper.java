//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslate;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoFull;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoLearn;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoPreview;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import org.mapstruct.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//@Mapper(componentModel = "spring")
//public abstract class CourseLectureMapper {
//
//    @Autowired
//    protected LectureProgressMapper lectureProgressMapper;
//
//    public abstract CourseLectureDtoPreview toDtoPreview(CourseLecture courseLecture, @Context Language lang);
//
//    public abstract List<CourseLectureDtoPreview> toDtoPreview(List<CourseLecture> courseLectures, @Context Language lang);
//
//    @Mapping(target = "lectureProgresses", ignore = true)
//    public abstract CourseLectureDtoLearn toDtoLearn(CourseLecture courseLecture, @Context Language lang, @Context Long userId);
//
//    @Mapping(target = "lectureProgresses", ignore = true)
//    public abstract List<CourseLectureDtoLearn> toDtoLearn(List<CourseLecture> courseLectures, @Context Language lang, @Context Long userId);
//
//    public abstract CourseLectureDtoFull toDtoFull(CourseLecture courseLecture, @Context Language lang);
//
//    public abstract List<CourseLectureDtoFull> toDtoFull(List<CourseLecture> courseLectures, @Context Language lang);
//
//    @AfterMapping
//    protected void enrichPreview(CourseLecture courseLecture,
//                                 @MappingTarget CourseLectureDtoPreview dto,
//                                 @Context Language lang) {
//        getTranslation(courseLecture, lang).ifPresent(t -> {
//            dto.setTitle(t.getTitle());
//            dto.setDescription(t.getDescription());
//        });
//    }
//
//    @AfterMapping
//    protected void enrichLearn(CourseLecture courseLecture,
//                               @MappingTarget CourseLectureDtoLearn dto,
//                               @Context Language lang,
//                               @Context Long userId) {
//        getTranslation(courseLecture, lang).ifPresent(t -> {
//            dto.setTitle(t.getTitle());
//            dto.setDescription(t.getDescription());
//            dto.setContentText(t.getContentText());
//            dto.setContentFileName(t.getContentFileName());
//        });
//
//        getUserProgress(courseLecture, userId).ifPresent(progress ->
//                dto.setUserCourseProgress(lectureProgressMapper.toDto(progress))
//        );
//    }
//
//    @AfterMapping
//    protected void enrichFull(CourseLecture courseLecture,
//                               @MappingTarget CourseLectureDtoFull dto,
//                               @Context Language lang) {
//        getTranslation(courseLecture, lang).ifPresent(t -> {
//            dto.setTitle(t.getTitle());
//            dto.setDescription(t.getDescription());
//            dto.setContentText(t.getContentText());
//            dto.setContentFileName(t.getContentFileName());
//        });
//    }
//
//    protected Optional<CourseLectureTranslate> getTranslation(CourseLecture courseLecture, Language lang) {
//        return courseLecture.getTranslations().stream()
//                .filter(t -> t.getLanguage().equals(lang))
//                .findFirst();
//    }
//
//    protected Optional<LectureProgress> getUserProgress(CourseLecture courseLecture, Long userId) {
//        return courseLecture.getLectureProgresses().stream()
//                .filter(p -> p.getEnrollment().getUser().getId().equals(userId))
//                .findFirst();
//    }
//}