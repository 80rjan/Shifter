//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslation;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureFullResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureLearningResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLecturePreviewResponse;
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
//    public abstract CourseLecturePreviewResponse toDtoPreview(CourseLecture courseLecture, @Context Language lang);
//
//    public abstract List<CourseLecturePreviewResponse> toDtoPreview(List<CourseLecture> courseLectures, @Context Language lang);
//
//    @Mapping(target = "lectureProgresses", ignore = true)
//    public abstract CourseLectureLearningResponse toDtoLearn(CourseLecture courseLecture, @Context Language lang, @Context Long userId);
//
//    @Mapping(target = "lectureProgresses", ignore = true)
//    public abstract List<CourseLectureLearningResponse> toDtoLearn(List<CourseLecture> courseLectures, @Context Language lang, @Context Long userId);
//
//    public abstract CourseLectureFullResponse toDtoFull(CourseLecture courseLecture, @Context Language lang);
//
//    public abstract List<CourseLectureFullResponse> toDtoFull(List<CourseLecture> courseLectures, @Context Language lang);
//
//    @AfterMapping
//    protected void enrichPreview(CourseLecture courseLecture,
//                                 @MappingTarget CourseLecturePreviewResponse dto,
//                                 @Context Language lang) {
//        getTranslation(courseLecture, lang).ifPresent(t -> {
//            dto.setTitle(t.getTitle());
//            dto.setDescription(t.getDescription());
//        });
//    }
//
//    @AfterMapping
//    protected void enrichLearn(CourseLecture courseLecture,
//                               @MappingTarget CourseLectureLearningResponse dto,
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
//                               @MappingTarget CourseLectureFullResponse dto,
//                               @Context Language lang) {
//        getTranslation(courseLecture, lang).ifPresent(t -> {
//            dto.setTitle(t.getTitle());
//            dto.setDescription(t.getDescription());
//            dto.setContentText(t.getContentText());
//            dto.setContentFileName(t.getContentFileName());
//        });
//    }
//
//    protected Optional<CourseLectureTranslation> getTranslation(CourseLecture courseLecture, Language lang) {
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